package zeev.fraiman.zefrastandart;

public class Link {
    private String linkName;
    private String linkDescription;
    private String linkType;
    private String linkID;

    public Link(String linkName,String linkDescription, String linkType, String linkID) {
        this.linkName = linkName;
        this.linkDescription = linkDescription;
        this.linkType = linkType;
        this.linkID = linkID;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkID() {
        return linkID;
    }

    public void setLinkID(String linkID) {
        this.linkID = linkID;
    }

    @Override
    public String toString() {
        return "Link{" +
                "linkName='" + linkName + '\'' +
                /*", linkDescription='" + linkDescription + '\'' +*/
                ", linkType='" + linkType + '\'' +
                ", linkID='" + linkID + '\'' +
                '}';
    }
}
