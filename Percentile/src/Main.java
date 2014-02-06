import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("scores.in");
		Scanner sc = new Scanner(f);
		ArrayList<Record> records = new ArrayList<Record>();
		String id;
		int score;

		while (sc.hasNext()) {
			id = sc.next();
			score = sc.nextInt();
			records.add(new Record(id, score));
		}

		Collections.sort(records, Record.recordIDComparator);
		
		sc.close();
		sc = new Scanner(System.in);
		
		System.out.print("Enter a student ID number to find percentile rank: ");
		id = sc.next();
		System.out.println("");
		score = getScoreFromID(records, id);
		if (score == -1) {
			System.out.println("Student ID not found.");
			return;
		}
		System.out.println("Student score " + id + " is " + score);
		Collections.sort(records, Record.recordScoreComparator);
		System.out.println("Student percentile ranking is " + getPercentileFromScore(records, score) + ".");
		
		sc.close();
		return;
		
	}
	
	static int getPercentileFromScore(ArrayList<Record> records, int score) {
		int a = 0, b = records.size()-1;
		int m = a/2 + b/2;
		int mScore = 0;
		
		while (a <= b) {
			m = a/2 + b/2;
			mScore = records.get(m).score;
			if (mScore == score) {
				return (int)Math.ceil(100 * (double)m / (double)records.size());
			} else if (mScore < score) {
				a = m+1;
			} else if (mScore > score) {
				b = m-1;
			}
		}
		
		return -1;
	}

	static int getScoreFromID (ArrayList<Record> records, String id) {
		int a = 0, b = records.size()-1;
		int m = a/2 + b/2;
		int studentID = 0;
		try {
			 studentID = Integer.parseInt(id);
		} catch (Exception e) {
			return -1;
		}
		while (a <= b) {
			m = a/2 + b/2;
			int mID = Integer.parseInt(records.get(m).id);
			if (mID == studentID) {
				return records.get(m).score;
			} else if (mID < studentID) {
				a = m+1;
			} else if (mID > studentID) {
				b = m-1;
			}
		}
		return -1;
	}
	
	static void printAll(ArrayList<Record> records) {
		int i = 1;
		for (Record r : records) {
			System.out.println("(" + i + ") " + r.id + ": " + r.score);
			i++;
		}

	}


}

class Record {
	String id;
	int score;
	public Record(String id, int score) {
		this.id = id;
		this.score = score;
	};

	public static Comparator<Record> recordIDComparator = new Comparator<Record>() {
		public int compare(Record a, Record b) {
			return a.id.compareTo(b.id);
		}
	};
	
	public static Comparator<Record> recordScoreComparator = new Comparator<Record>() {
		public int compare(Record a, Record b) {
			if (a.score > b.score) {
				return 1;
			} else if (a.score == b.score) {
				return 0;
			} else {
				return -1;
			}
		}
	};
}