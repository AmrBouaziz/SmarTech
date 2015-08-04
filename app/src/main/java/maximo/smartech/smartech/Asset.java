package maximo.smartech.smartech;

/**
 * Created by amrou on 04/08/15.
 */
public class Asset {
    private String assetID ;
    private String assetNum ;
    private String asset ;
    private String Category;

    public Asset(String assetID, String assetNum, String asset, String category) {
        this.assetID = assetID;
        this.assetNum = assetNum;
        this.asset = asset;
        Category = category;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(String assetNum) {
        this.assetNum = assetNum;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Asset() {
    }
}
