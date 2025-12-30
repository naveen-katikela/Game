import java.util.*;

class Solution {

    char[][] mat;
    int[][] diag;
    int[] row_obstacle;
    int[] col_obstacle;
    Map<String, List<int[]>> hs;
    List<String> colors;
    boolean found;

    public Solution(int n) {
        mat = new char[n][n];
        diag = new int[n][n];
        row_obstacle = new int[n];
        col_obstacle = new int[n];
        hs = new HashMap<>();
        found = false;
        for(int i = 0; i < mat.length; ++i) {
            for(int j = 0; j < mat[0].length; ++j) {
                mat[i][j] = ' ';
            }
        }
    }

    public void sort_colors() {

        colors = new ArrayList<>(hs.keySet());

        colors.sort((c1, c2) -> 
            Integer.compare(
                hs.get(c1).size(), hs.get(c2).size()
        ));

    }

    public void init_hs(String s, List<int[]> co_ordinates) {
        hs.put(s, new ArrayList<>(co_ordinates));
    }

    public String ret_color() {
        return colors.get(0);
    }

    public void dfs(int curr) {

        if(found)
            return;
        if(curr == colors.size()) {
            found = true;
            return;
        }

        int i;
        int j;

        for(int[] p : hs.get(colors.get(curr))) {
            i = p[0];
            j = p[1];
            if(diag[i][j] == 0 && !found && row_obstacle[i] == 0 && col_obstacle[j] == 0) {
                mat[i][j] = 'Q';
                if(i + 1 < mat.length && j + 1 < mat[0].length) diag [i + 1][j + 1]++;
                if(i + 1 < mat.length && j - 1 >= 0) diag [i + 1][j - 1]++;
                if(i - 1 >= 0 && j - 1 >= 0) diag [i - 1][j - 1]++;
                if(i - 1 >= 0 && j + 1 < mat[0].length) diag [i - 1][j + 1]++;
                row_obstacle[i] = 1;
                col_obstacle[j] = 1;
                dfs(curr + 1);
                if(!found) {
                    mat[i][j] = ' ';
                    if(i + 1 < mat.length && j + 1 < mat[0].length) diag [i + 1][j + 1]--;
                    if(i + 1 < mat.length && j - 1 >= 0) diag [i + 1][j - 1]--;
                    if(i - 1 >= 0 && j - 1 >= 0) diag [i - 1][j - 1]--;
                    if(i - 1 >= 0 && j + 1 < mat[0].length) diag [i - 1][j + 1]--;
                    row_obstacle[i] = 0;
                    col_obstacle[j] = 0;
                }
            }
        }

        return;
    }

    public void print_matrix() {
        for(int i = 0; i < mat.length; ++i) {
            for(int j = 0; j < mat[0].length; ++j) {
                System.out.print("[" +mat[i][j] + "]");
            }
            System.out.println();
        }
    }
}

public class code {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the dimension of the matrix (nxn). n ?? ");

        int n = sc.nextInt();

        Solution s = new Solution(n);

        System.out.println("Enter the number of regions ??");

        int r = sc.nextInt();

        int x;
        int y;
        String color = "";

        sc.nextLine(); 

        for (int i = 0; i < r; ++i) {

            System.out.println("Enter the colour of the region and co-ordinates below:");
            color = sc.nextLine();

            List<int[]> l = new ArrayList<>();

            do {
                x = sc.nextInt();
                y = sc.nextInt();
                if(x != -1 && y != -1)
                    l.add(new int[]{x, y});
            } while (x != -1 && y != -1);

            sc.nextLine();  
            s.init_hs(color, l);
        }



        s.sort_colors();
        s.dfs(0);
        s.print_matrix();
    }   
}