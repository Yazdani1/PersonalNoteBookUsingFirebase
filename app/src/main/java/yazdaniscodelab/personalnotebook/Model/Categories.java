package yazdaniscodelab.personalnotebook.Model;

/**
 * Created by Yazdani on 3/26/2018.
 */

public class Categories {

    String id;
    String categoriesName;
    String mDate;

    public Categories(){

    }

    public Categories(String id, String categoriesName, String mDate) {
        this.id = id;
        this.categoriesName = categoriesName;
        this.mDate = mDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getmDate(){
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

}
