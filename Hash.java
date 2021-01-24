import java.util.Scanner;
import java.io.*;
import java.util.Arrays;

public class Hash {
	private static final int TABLE_SIZE_COLUMN = 510;
	private static final int TABLE_SIZE_ROW = 10;
	private static int compareCount = 0;
	private static int collisionCount = 0;

	public static void main(String[] args) {
		// Открыть файл, получить массив словаря из файла
		String filename = "input.txt";
		String array[] = getArray(filename);

		// Создать таблицу хэшей из словаря
		// Если по значению хэша занято, то сохранить в следующий индекс
		int table[][] = getTable(array);
		// 
		// Ввод строки
		System.out.print("Enter name: ");
		// String query = new Scanner(System.in).next();
		String query = "ROZA";

		// Вычисление хэша из входной строки
		// Поиск индекса в таблице хэшей по значению хэша
		int[] result = search(query, array, table);
		// Вывод расположение искомой строки
		System.out.println("Collisions = " + collisionCount);
		System.out.println("Compares = " + compareCount);
		System.out.println("Result : " + Arrays.toString(result));
	}

	static int[] search(String value, String[] array, int[][] table) {
		int[] result = new int[2];
		final int hash = getHash(value);
		int i = 0;

		// В случае если ячейка != -1
		// сохраняем номер индекса
		while (table[hash][i] != -1) {
			if (value.equals(array[table[hash][i]])) {
				compareCount++;
				result[0] = hash;
				result[1] = i;
				return result;
			} else {
				i++;
			}
		}
		return null;
	}

	// Вычисление хэша
	static int getHash(String value) {
		int hash = value.codePointAt(0) + value.codePointAt(1);
		System.out.printf("hash(%s) = %d\n", value, hash);
		return hash;
	}

	// Получение массива из файла input.txt
	static String[] getArray(String filename) {
		String array[] = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			line = reader.readLine();
			line = line.toUpperCase();
			System.out.println("File content:\n\t" + line);
			array = line.split(";");
		} catch (IOException e) {
			e.getMessage();
		}

		// array[] = {"Vlad", "Katya", "Vasya", "Karina", "Roma"};
		return array;
	}

	static int[][] getTable(String[] array) {
		int table[][] = new int[TABLE_SIZE_COLUMN][TABLE_SIZE_ROW];
		// Пустые ячейки заполняются -1 для удобства
		// потому что потому поиск массива начинается с 0
		for (int i=0; TABLE_SIZE_COLUMN>i; i++) 
			for (int j=0; TABLE_SIZE_ROW>j; j++) 
				table[i][j] = -1;
		
		int hash = 0;
		int index = 0;
		// 2. Вычислить хэш для каждой записи
		int i = 0;
		for (String s : array) {
			hash = getHash(s);
			index = 0;
			// Если ячейка уже заполнена, пропускаем ее
			// увеличиваем index + 1, и сравниваем следующий элемент
			while (table[hash][index] != -1) {
				index++;
				collisionCount++;
			}
			table[hash][index] = i++;
		}
		// System.out.println(Arrays.deepToString(table));
		return table;
	}
}
