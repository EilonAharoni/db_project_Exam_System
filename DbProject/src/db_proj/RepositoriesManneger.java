package db_proj;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class RepositoriesManneger {
private QuestionesRepository[] repositories;
private int numRepositories;
private final int DEAFULT_SIZE=5;

	public RepositoriesManneger() {
		this.repositories = new QuestionesRepository[DEAFULT_SIZE];
	
	}
	
	private static void saveRepositories(QuestionesRepository[] repositories, String filename) throws IOException {
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filename));
		file.writeObject(repositories);
		file.close();
	}
	
	
	private static QuestionesRepository[] loadRepositories(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream file = new ObjectInputStream(new FileInputStream(filename));
		QuestionesRepository[] repositories = (QuestionesRepository[])file.readObject();
		file.close();
		return repositories;
		}


	public void initRepositories() throws IOException, ClassNotFoundException {
		
		// HARD CODE BECAUSE IT IS NOT STABLE
		repositories[0] = new QuestionesRepository("Math");
		repositories[0].initHardCode();
		repositories[1] = new QuestionesRepository("Capitals");
		repositories[1].initHardCode();
		repositories[2] = new QuestionesRepository("create new repository");
		numRepositories = 3;
		saveRepositories(repositories, "repository.dat");
//		
		// read repositories from binary file
		repositories = loadRepositories("repository.dat");
		boolean found = false;
		for (int i = 0; i < DEAFULT_SIZE; i++) {
			if (repositories[i] == null) {
				numRepositories = i;
				found = true;
				break;
			}
		}
		if (!found) {
			numRepositories = DEAFULT_SIZE;
		}
		
   }
	
	public QuestionesRepository getRepository(int index) {
		return repositories[index];
	}
	
	public int getNumRepositories() {
		return numRepositories;
	}
	
	
	
	
	public String toString() {
		String ans = "";
		for (int i = 0; i < numRepositories; i++) {
			ans +=  (i+1)+ ") "  + repositories[i].getSubject() + "\n";
		}
		return ans;
		
	}
	public boolean addRepository(String newRepoSubject) {
		for (int i = 0; i < numRepositories; i++) {
			if (repositories[i].getSubject().equals(newRepoSubject)) // this question alredy exist
				return false;
		}
		
		if (numRepositories == repositories.length)
			repositories = Arrays.copyOf(repositories, numRepositories * 2);

		repositories[numRepositories-1] = new QuestionesRepository(newRepoSubject);
		repositories[numRepositories++] = new QuestionesRepository("create new repository");
		return true;
	}

}
