package BingoGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day04SquidBingo {

	public static void main(String[] args) {
		File file = new File("res/bingoTestCode.txt");

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));

			List<String> list = new ArrayList<>();

			String in;
			while ((in = reader.readLine()) != null)
				list.add(in);

			reader.close();

			String[] numbers = list.get(0).split(",");

			List<int[][]> boards = generateBoards(list);

			int lastNumber;
			
			//part one
			for (String numStr : numbers) {
				lastNumber = Integer.parseInt(numStr);

				markingBoard(boards, lastNumber);
				
				boolean done = false;
				for (int[][] board : boards) {
					if (isBoardWon(board)) {
						long answer = calculation(board);
						System.out.println("part one: " + answer * lastNumber);
						done = true;
						break;
					}
				}
				if (done) {
					break;
				}
			}

			// part two
			boards = generateBoards(list);
			long answer = 0;
			for (String numStr : numbers) {
				lastNumber = Integer.parseInt(numStr);
				markingBoard(boards, lastNumber);
				for (int i = boards.size() - 1; i >= 0; i--) {
					if (isBoardWon(boards.get(i))) {
						
						if (boards.size() == 1) {
							long totalSum = calculation(boards.get(i));
							answer = totalSum * lastNumber;
							System.out.println("part two: " + answer);
							return;
						}
						boards.remove(i);
					}
					
				}
			}
			

		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isBoardWon(int[][] checkBoard) {
		boolean won = false;
		for (int row = 0; row < 5; row++) {
			won = true;
			for (int col = 0; col < 5; col++) {
				if (checkBoard[row][col] != -1) {
					won = false;
					break;
				}
			}
			if (won) {
				return won;
			}
		}
		for (int col = 0; col < 5; col++) {
			won = true;
			for (int row = 0; row < 5; row++) {
				if (checkBoard[row][col] != -1) {
					won = false;
					break;
				}
			}
			if (won) {
				return won;
			}
		}
		return won;

	}

	public static long partTwo(List<int[][]> boards) {
		for (int i = boards.size(); i >= 0; i--) {
			if (isBoardWon(boards.get(i))) {
				if (boards.size() == 1) {
					long lastBoard = 0;
					lastBoard += (calculation(boards.get(i)));
					return lastBoard;
				}
				boards.remove(i);
			}
		}
		return 0;
	}

	public static long calculation(int[][] board) {
		long totalSum = 0;
		for (int[] row : board) {
			for (int col : row) {
				if (col != -1) {
					totalSum += col;
				}
			}
		}
		return totalSum;
	}

	public static void markingBoard(List<int[][]> boards, int number) {

		for (int[][] board : boards) {

			for (int row = 0; row < 5; row++) {
				for (int col = 0; col < 5; col++) {
					if (board[row][col] == number) {
						board[row][col] = -1;
					}
				}
			}
		}
	}

	public static List<int[][]> generateBoards(List<String> list) {
		List<int[][]> boards = new ArrayList<>();

		int firstLine = 2;
		int currentLine;

		for (int i = 0; i < 5; i++) {
			currentLine = firstLine + i;

			while (currentLine < list.size()) {
				int[][] board;

				if (i == 0) {
					board = new int[5][5];
					boards.add(board);
				} else {
					board = boards.get((currentLine - i - firstLine) / 6);
				}
				String line = list.get(currentLine);
				for (int j = 0; j < 5; j++) {

					int number = Integer.parseInt(line.substring(j * 3, j * 3 + 2).trim());

					board[i][j] = number;
				}
				currentLine += 6;
			}
		}
		return boards;
	}

}
