package la.renzhen.basis.utils;

import java.util.Optional;

import static la.renzhen.basis.utils.Strings.isNullOrEmpty;

public class Version {

    /**
     * 对比版本，version1大于version2时，返回1<p>
     * 判断的版本号必须符合：X.X.X规则
     *
     * @param version1 版本1
     * @param version2 版本2
     * @return 1，0，-1
     */
    public static int compare(String version1, String version2) throws Exception {
        if (isNullOrEmpty(version1)) {
            return -1;
        }
        if (isNullOrEmpty(version2)) {
            return 1;
        }
        String[] editions1 = version1.split("\\.", 3);
        String[] editions2 = version2.split("\\.", 3);

        int c = Integer.valueOf(editions1[0]).compareTo(Integer.parseInt(editions2[0]));
        if (c != 0) {
            return c;
        }
        c = Integer.valueOf(editions1[1]).compareTo(Integer.parseInt(editions2[1]));
        if (c != 0) {
            return c;
        }
        return Integer.valueOf(editions1[2]).compareTo(Integer.parseInt(editions2[2]));
    }
}
