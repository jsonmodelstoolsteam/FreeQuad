package application.stages.components;

public class JFile {
    private String name;
    private String text;
    private String replace;
    private boolean revert;

    public JFile(String name, String text) {
        this.revert = false;
        this.name = name;
        this.text = text;
    }

    public JFile(String name, String text, String replace) {
        this(name, text);
        this.replace = replace;
    }

    public JFile setReplace(String replace) {
        this.replace = replace;
        return this;
    }

    public JFile edit(String text) {
        this.text = text;
        return this;
    }

    public JFile edit(String name, String text) {
        this.name = name;
        this.text = text;

        return this;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getReplace() {
        return replace;
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
    }

    public boolean isRevert() {
        return revert;
    }
}