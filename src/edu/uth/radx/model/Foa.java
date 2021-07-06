package edu.uth.radx.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Foa {
	AUTOMATIC("Automatic Detection and Tracing"),
	CHEMOSENSORY("Chemosensory Testing"),
	EXOSOME("Exosome Based"),
	MULTIMODAL("Multimodal Surveillance"),
	NOVEL("Novel Biosensing and VOC"),
	PREVAILKIDS("PreVAIL KIds"),
	SCENT("SCENT"),
	WASTEWATER("Wastewater"),
	NIEHS("NIEHS Diagnostic/Prognostic RNAseq");

    private String description;
    
    Foa(String description) {
        this.description = description;
    }

}
