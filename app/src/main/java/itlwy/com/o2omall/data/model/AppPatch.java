package itlwy.com.o2omall.data.model;

import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 */
public class AppPatch {
    private String version;
    private List<Patch> patch;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Patch> getPatch() {
        return patch;
    }

    public void setPatch(List<Patch> patch) {
        this.patch = patch;
    }

    public static class Patch{
        private String name;
        private String uri;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    public boolean isContain(Patch other){
        boolean flag = false;
        for(Patch item:patch){
            if (item.getName().equals(other.getName())){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
