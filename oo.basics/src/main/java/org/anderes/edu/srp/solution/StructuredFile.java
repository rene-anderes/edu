package org.anderes.edu.srp.solution;

public class StructuredFile {
    private String name;
    private String suffix;

    public StructuredFile(String name) {
        this.name = name;
        this.suffix = name.substring(name.lastIndexOf(".") + 1, name.length());
    }

    public String getContent() {
        return "content";
    }

    public String getNameWithoutSuffix() {
        int endIndex = name.lastIndexOf(".");
        return name.substring(0, endIndex);
    }

    public String getSuffix() {
        return this.suffix;
    }
    
    public void writeContent(String content) {
	// do nothing
    }
}
