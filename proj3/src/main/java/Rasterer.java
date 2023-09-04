import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /**
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile, as the images in the img/ folder are scraped.
     * Longitude == x-axis; latitude == y-axis.
     * the static variables below are for the d0_x0_y0
     */
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;

    /** Each tile is 256x256 pixels. */
    public static final int TILE_SIZE = 256;
    public static String[][] render_grid;
    public double wholeMapLon = ROOT_LRLON - ROOT_ULLON;
    public double wholeMapLat = ROOT_ULLAT - ROOT_LRLAT;
    public boolean query_success;
    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        query_success = validInput(params);
        Map<String, Object> results = new HashMap<>();
        //now we figure out the correct depth for the query
        double LonDPP = (double) ((params.get("lrlon") - params.get("ullon")) * 288200 / params.get("w"));
        System.out.println(LonDPP);
        int depth = getLevel(LonDPP);
        System.out.println(depth);
        results.put("depth", depth);
        //how can we know which tile we choose to use?
        int ulx = (int) ((params.get("ullon") - ROOT_ULLON ) / (wholeMapLon / Math.pow(2, depth)));
        int lrx = (int) ((params.get("lrlon") - ROOT_ULLON) / (wholeMapLon / Math.pow(2, depth)));
        int uly = (int) ((ROOT_ULLAT - params.get("ullat")) / (wholeMapLat / Math.pow(2, depth)));
        int lry = (int) ((ROOT_ULLAT - params.get("lrlat")) / (wholeMapLat / Math.pow(2, depth)));
        System.out.println(ulx);
        System.out.println(uly);
        System.out.println(lrx);
        System.out.println(lry);
        int totalY = lrx - ulx + 1;
        int totalX = lry - uly + 1;

        if (totalX > Math.pow(2, depth)) {
            totalX = (int)(Math.pow(2, depth));
        }
        if (totalY > Math.pow(2, depth)) {
            totalY = (int)(Math.pow(2, depth));
        }
        System.out.println(totalX);
        System.out.println(totalY);
        render_grid = new String[totalX][totalY];

        int tempX = ulx;
        int tempY = uly;
        for (int i = 0; i < totalX; i += 1) {
            for (int j = 0; j < totalY; j += 1) {
                String x = "d" + depth + "_x" + tempX + "_y" + tempY + ".png";
                render_grid[i][j] = x;
                tempX += 1;
            }
            tempX = ulx;
            tempY += 1;
        }
        results.put("render_grid", render_grid);
        // add raster_ul_lon, raster_ul_lat, raster_lr_lon and raster_lr_lat
        double raster_ul_lon = (wholeMapLon / Math.pow(2, depth)) * ulx - ROOT_ULLON;
        double raster_lr_lon = (wholeMapLon / Math.pow(2, depth)) * (tempX) - ROOT_ULLON;
        double raster_ul_lat = ROOT_ULLAT - (wholeMapLat / Math.pow(2, depth)) * uly;
        double raster_lr_lat = ROOT_ULLAT - (wholeMapLat / Math.pow(2, depth)) * (tempY);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("query_success", query_success);
        return results;
    }

    /**
     * method to determine depth
     * @param inputWidth
     * @return depth
     */
    private int getLevel(double inputWidth) {
        double res = 99;
        int level = 0;
        while (res > inputWidth) {
            if (level >= 7) {
                return 7;
            }
            res = res / Math.pow(2, 1);
            level += 1;
        }
        return level;
    }
    // valid the user's input
    private boolean validInput(Map<String, Double> params) {
        if ((params.get("ullon") - ROOT_ULLON) > 1 || (ROOT_ULLAT - params.get("ullat")) > 1) {
            return false;
        }
        if ((params.get("lrlon") - ROOT_ULLON) > 1 || (ROOT_ULLAT - params.get("lrlat")) > 1) {
            return false;
        }
        if (params.get("ullon") > params.get("lrlon") || params.get("lrlat") > params.get("ullat")) {
            return false;
        }
        return true;
    }
    /**
    public static void main(String[] args) {
        Rasterer r = new Rasterer();
        Map<String, Double> map = new HashMap();
        map.put("ullon", -122.28860942832348);
        map.put("lrlon", -122.23961235262233);
        map.put("w", 671.1199949960427);
        map.put("h", 656.691215075149);
        map.put("ullat", 37.8592895354612);
        map.put("lrlat", 37.850187716549755);
        System.out.println(r.getMapRaster(map));
    }
    */
}
