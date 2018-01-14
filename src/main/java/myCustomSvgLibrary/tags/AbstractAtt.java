package myCustomSvgLibrary.tags;

abstract class AbstractAtt {
    String name;

    public AbstractAtt(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String render(){
        return this.getName()+"=\""+this.getValue()+"\"";
    }

    abstract public String getValue();
}