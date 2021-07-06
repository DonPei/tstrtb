package edu.uth.radx.model;

import java.util.ArrayList;
import java.util.List;

import edu.uth.kit.project.CsvReaderWriter;
import edu.uth.radx.model.entities.Concept;
import edu.uth.radx.model.entities.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Dictionary {

	private List<Question> questions;

	// private CsvReaderWriter csvReaderWriter;
	private Concept[] concepts1;
	private int sectionHeaderColIndex = 2;

	public Dictionary(String inputFileName) {
		read(inputFileName);
	}

	private void read(String fileName) {
		CsvReaderWriter csvReaderWriter = new CsvReaderWriter();
		csvReaderWriter.read(fileName, true);
		List<String[]> rows = csvReaderWriter.getRows();
		String prevNotNullSectionHeader = "Identity";
		questions = new ArrayList<>();
		for (int i = 0; i < rows.size(); i++) {
			String[] r = rows.get(i);
			String sectionHeader = r[sectionHeaderColIndex].trim();
			if (sectionHeader == null || sectionHeader.isEmpty()) {
				r[2] = prevNotNullSectionHeader;
			} else {
				prevNotNullSectionHeader = sectionHeader;
			}
			questions.add(genQuestion(r));
		}

		// Map<String, String> map = new LinkedHashMap<>();
		// for (int i = 0; i < questions.size(); i++) {
		// map.put(questions.get(i).getSectionHeader(),
		// questions.get(i).getSectionHeader());
		// }
		// Set<String> set = map.keySet();
		// concepts = new Concept[set.size()];
		// int k = 0;
		// for (String s : set) {
		// Concept concept = new Concept();
		// concept.setId(k + "");
		// concept.setName(s);
		// concept.setQuestions(getQuestion(s, questions));
		// concepts[k++] = concept;
		// }
	}

	private Question[] getQuestion(String sectionHeader, List<Question> list) {
		List<Question> questions = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Question q = list.get(i);
			if (q.getSectionHeader().equals(sectionHeader)) {
				questions.add(q);
			}
		}

		Question[] qs = new Question[questions.size()];
		for (int i = 0; i < questions.size(); i++) {
			qs[i] = questions.get(i);
		}

		return qs;
	}

	private Question genQuestion(String[] row) {
		Question question = new Question();
		question.setRow(row);
		return question;
	}

	// public String[] getConceptNames() {
	// String[] names = new String[concepts.length];
	// for (int i = 0; i < concepts.length; i++) {
	// names[i] = concepts[i].getName();
	// }
	// return names;
	// }
	//
	// public String[] getQuestionNames(int conceptIndex) {
	// Question[] questions = concepts[conceptIndex].getQuestions();
	// String[] names = new String[questions.length];
	// for (int i = 0; i < questions.length; i++) {
	// names[i] = questions[i].getName();
	// }
	// return names;
	// }

	public String genTestData() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < questions.size(); i++) {
			String[] r = questions.get(i).getRow();
			sb.append("\"" + r[0] + "\"");
			if (i < questions.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("\n");

		String[][] data = new String[3][questions.size()];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				data[i][j] = i + "_" + j;
			}
		}

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				sb.append("\"" + data[i][j] + "\"");
				if (j < (data[i].length - 1)) {
					sb.append(",");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < questions.size(); i++) {
			String[] r = questions.get(i).getRow();

			sb.append("\"" + r[0] + "\"");
			if (i < questions.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("\n");

		return sb.toString();
	}

	// public void writeCsv(String outputFileName) {
	// csvReaderWriter.write(outputFileName);
	// }

	// public void write(String outputFileName) {
	// ObjectMapper objectMapper = new ObjectMapper();
	//
	// ObjectNode rootNode = objectMapper.createObjectNode().objectNode();
	//
	// ArrayNode conceptNode = objectMapper.createArrayNode();
	// for (Concept concept : concepts) {
	// ObjectNode oNode = concept.createObjectNode(objectMapper);
	// conceptNode.add(oNode);
	// }
	//
	// rootNode.set("concept", conceptNode);
	//
	// ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
	// try {
	// writer.writeValue(new File(outputFileName), rootNode);
	// } catch (JsonGenerationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JsonMappingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// public String genData() {
	// StringBuilder sb = new StringBuilder();
	// List<String[]> rows = csvReaderWriter.getRows();
	// for (int i = 0; i < rows.size(); i++) {
	// String[] row = rows.get(i);
	// sb.append(row[0]);
	// if (i < rows.size() - 1) {
	// sb.append("\n");
	// }
	// }
	//
	// return sb.toString();
	// }

}
