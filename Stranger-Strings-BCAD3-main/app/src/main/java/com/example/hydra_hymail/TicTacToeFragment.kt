package com.example.hydra_hymail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class TicTacToeFragment : Fragment() {

    private lateinit var board: Array<Array<Button>>
    private lateinit var playerScoreText: TextView
    private lateinit var computerScoreText: TextView
    private lateinit var resetButton: Button
    private lateinit var backButton: Button
    private lateinit var difficultySpinner: Spinner

    private var playerTurn = true
    private var playerScore = 0
    private var computerScore = 0
    private var currentUserId: String? = null

    private var difficulty: Difficulty = Difficulty.MEDIUM

    enum class Difficulty { EASY, MEDIUM, HARD }

    companion object {
        private const val ARG_USER_ID = "arg_user_id"
        fun newInstance(userId: String): TicTacToeFragment {
            val f = TicTacToeFragment()
            val args = Bundle()
            args.putString(ARG_USER_ID, userId)
            f.arguments = args
            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUserId = arguments?.getString(ARG_USER_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_tictactoe, container, false)

        board = arrayOf(
            arrayOf(root.findViewById(R.id.button00), root.findViewById(R.id.button01), root.findViewById(R.id.button02)),
            arrayOf(root.findViewById(R.id.button10), root.findViewById(R.id.button11), root.findViewById(R.id.button12)),
            arrayOf(root.findViewById(R.id.button20), root.findViewById(R.id.button21), root.findViewById(R.id.button22))
        )

        playerScoreText = root.findViewById(R.id.playerScore)
        computerScoreText = root.findViewById(R.id.computerScore)
        resetButton = root.findViewById(R.id.resetButton)
        backButton = root.findViewById(R.id.backButton)
        difficultySpinner = root.findViewById(R.id.difficultySpinner)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.ttt_difficulties,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        difficultySpinner.adapter = adapter
        difficultySpinner.setSelection(1)

        difficultySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                difficulty = when (position) {
                    0 -> Difficulty.EASY
                    1 -> Difficulty.MEDIUM
                    2 -> Difficulty.HARD
                    else -> Difficulty.MEDIUM
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        for (r in 0..2) {
            for (c in 0..2) {
                board[r][c].setOnClickListener { onCellClicked(r, c) }
            }
        }

        resetButton.setOnClickListener { resetBoard() }
        backButton.setOnClickListener { parentFragmentManager.popBackStack() }

        resetBoard()
        updateScores()
        return root
    }

    private fun onGameWonByUser() {
        val activity = requireActivity() as? BadgesActivity
        val uid = currentUserId

        if (uid != null && activity != null) {
            activity.addPointsToLeaderboard(uid, 10)
            activity.unlockTttBadge()
            activity.recordRoundPlayedForUser(uid)
        }

        Toast.makeText(requireContext(), "🎉 You won! +50 points & badge check", Toast.LENGTH_SHORT).show()
    }

    private fun onCellClicked(r: Int, c: Int) {
        if (!playerTurn || board[r][c].text.isNotEmpty()) return

        placeMove(r, c, "X")
        if (checkWinner("X")) {
            playerScore++
            updateScores()
            onGameWonByUser()
            view?.postDelayed({ resetBoard() }, 700L)
            return
        }

        if (isBoardFull()) {
            val uid = currentUserId
            if (uid != null) {
                (activity as? BadgesActivity)?.addPointsToLeaderboard(uid, 1)
                (activity as? BadgesActivity)?.recordRoundPlayedForUser(uid)
            }
            Toast.makeText(requireContext(), "It's a draw!", Toast.LENGTH_SHORT).show()
            view?.postDelayed({ resetBoard() }, 700L)
            return
        }

        playerTurn = false
        view?.postDelayed({ computerMove() }, 350L)
    }

    private fun placeMove(r: Int, c: Int, symbol: String) {
        board[r][c].text = symbol
    }

    private fun checkWinner(symbol: String): Boolean {
        // Rows and columns
        for (i in 0..2) {
            if ((board[i][0].text == symbol && board[i][1].text == symbol && board[i][2].text == symbol) ||
                (board[0][i].text == symbol && board[1][i].text == symbol && board[2][i].text == symbol)) return true
        }
        // Diagonals
        if ((board[0][0].text == symbol && board[1][1].text == symbol && board[2][2].text == symbol) ||
            (board[0][2].text == symbol && board[1][1].text == symbol && board[2][0].text == symbol)) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (r in 0..2) for (c in 0..2) if (board[r][c].text.isEmpty()) return false
        return true
    }

    private fun computerMove() {
        val move = when (difficulty) {
            Difficulty.EASY -> randomMove()
            Difficulty.MEDIUM -> mediumMove()
            Difficulty.HARD -> hardMove()
        }

        if (move != null) {
            val (r, c) = move
            placeMove(r, c, "O")

            if (checkWinner("O")) {
                computerScore++
                updateScores()
                Toast.makeText(requireContext(), "💻 Computer won!", Toast.LENGTH_SHORT).show()
                view?.postDelayed({ resetBoard() }, 700L)
                return
            }

            playerTurn = true
        }
    }

    private fun randomMove(): Pair<Int, Int>? {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (r in 0..2) for (c in 0..2) if (board[r][c].text.isEmpty()) emptyCells.add(r to c)
        return if (emptyCells.isNotEmpty()) emptyCells.random() else null
    }

    private fun mediumMove(): Pair<Int, Int>? {
        val winMove = findWinningMove("O")
        if (winMove != null) return winMove

        val blockMove = findWinningMove("X")
        if (blockMove != null) return blockMove

        return randomMove()
    }

    private fun hardMove(): Pair<Int, Int>? = minimaxMove()

    private fun findWinningMove(symbol: String): Pair<Int, Int>? {
        for (r in 0..2) for (c in 0..2) {
            if (board[r][c].text.isEmpty()) {
                board[r][c].text = symbol
                val win = checkWinner(symbol)
                board[r][c].text = ""
                if (win) return r to c
            }
        }
        return null
    }

    private fun minimaxMove(): Pair<Int, Int>? {
        var bestScore = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        for (r in 0..2) for (c in 0..2) if (board[r][c].text.isEmpty()) {
            board[r][c].text = "O"
            val score = minimax(0, false)
            board[r][c].text = ""
            if (score > bestScore) {
                bestScore = score
                bestMove = r to c
            }
        }
        return bestMove
    }

    private fun minimax(depth: Int, isMaximizing: Boolean): Int {
        if (checkWinner("O")) return 10 - depth
        if (checkWinner("X")) return depth - 10
        if (isBoardFull()) return 0

        return if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (r in 0..2) for (c in 0..2) if (board[r][c].text.isEmpty()) {
                board[r][c].text = "O"
                bestScore = max(bestScore, minimax(depth + 1, false))
                board[r][c].text = ""
            }
            bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (r in 0..2) for (c in 0..2) if (board[r][c].text.isEmpty()) {
                board[r][c].text = "X"
                bestScore = min(bestScore, minimax(depth + 1, true))
                board[r][c].text = ""
            }
            bestScore
        }
    }

    private fun resetBoard() {
        for (r in 0..2) for (c in 0..2) board[r][c].text = ""
        playerTurn = true
    }

    private fun updateScores() {
        playerScoreText.text = "Player: $playerScore"
        computerScoreText.text = "Computer: $computerScore"
    }
}
