package jackfrancisdalton.com.cursor2pojo;

public class ResultDescriber {

    public enum StructureType {
        list,
        object
    }

    private StructureType structureType;
    private Class<?> desiredClass;

    public ResultDescriber(StructureType structureType, Class<?> desiredClass) {
        this.structureType = structureType;
        this.desiredClass = desiredClass;
    }

    public StructureType getStructureType() {
        return structureType;
    }

    public Class<?> getDesiredClass() {
        return desiredClass;
    }
}