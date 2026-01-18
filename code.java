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

        for (int i = 0; i < mat.length; ++i) {
            for (int j = 0; j < mat[0].length; ++j) {
                mat[i][j] = ' ';
            }
        }
    }
    
    public void sort_colors() {
        colors = new ArrayList<>(hs.keySet());
        colors.sort((c1, c2) ->
                Integer.compare(hs.get(c1).size(), hs.get(c2).size())
        );
    }

    public void init_hs(String s, List<int[]> co_ordinates) {
        hs.put(s, new ArrayList<>(co_ordinates));
    }

    public void dfs(int curr) {

        if (found) return;

        if (curr == colors.size()) {
            found = true;
            return;
        }

        int i, j;
        String cl = colors.get(curr);

        for (int[] p : hs.get(cl)) {
            i = p[0];
            j = p[1];

            if (diag[i][j] == 0 && row_obstacle[i] == 0 && col_obstacle[j] == 0) {

                mat[i][j] = 'Q';
                diag[i][j]++;

                if(i + 1 < mat.length && j + 1 < mat[0].length) diag[i + 1][j + 1]++;
                if(i + 1 < mat.length && j - 1 >= 0) diag[i + 1][j - 1]++;
                if(i - 1 >= 0 && j - 1 >= 0) diag[i - 1][j - 1]++;
                if(i - 1 >= 0 && j + 1 < mat[0].length) diag[i - 1][j + 1]++;

                row_obstacle[i] = 1;
                col_obstacle[j] = 1;

                dfs(curr + 1);

                if (!found) {

                    mat[i][j] = ' ';
                    diag[i][j]--;

                    if(i + 1 < mat.length && j + 1 < mat[0].length) diag[i + 1][j + 1]--;
                    if(i + 1 < mat.length && j - 1 >= 0) diag[i + 1][j - 1]--;
                    if(i - 1 >= 0 && j - 1 >= 0) diag[i - 1][j - 1]--;
                    if(i - 1 >= 0 && j + 1 < mat[0].length) diag[i - 1][j + 1]--;

                    row_obstacle[i] = 0;
                    col_obstacle[j] = 0;
                }
            }
        }
    }

    public void print_matrix() {
        for (int i = 0; i < mat.length; ++i) {
            for (int j = 0; j < mat[0].length; ++j) {
                System.out.print("[" + mat[i][j] + "]");
            }
            System.out.println();
        }
    }
}

public class code {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the dimension of the matrix (nxn). n ?? ");
        int n = sc.nextInt();

        Solution s = new Solution(n);

        System.out.print("Enter the number of regions ?? ");
        int r = sc.nextInt();

        int x, y;
        String color = "";

        sc.nextLine();

        for (int i = 0; i < r; ++i) {

            color = sc.nextLine().trim();

            if (color.isEmpty()) {
                i--;
                continue;
            }

            List<int[]> l = new ArrayList<>();

            do {
                x = sc.nextInt();
                y = sc.nextInt();

                if (x != -1 && y != -1) {
                    l.add(new int[]{x, y});
                }

            } while (x != -1 && y != -1);

            sc.nextLine(); 
            s.init_hs(color, l);
        }

        s.sort_colors();
        s.dfs(0);
        s.print_matrix();
    }
}


/* 
Purple 
0 0 0 1 0 2 0 3 1 0 1 1 1 2 2 0 2 1 3 0 -1 -1 
Cement 
6 0 7 0 7 1 -1 -1 
White 
4 2 5 1 5 2 5 3 6 1 6 2 -1 -1 
Red 
4 7 5 7 6 7 7 7 7 6 -1 -1 
Yellow 
5 6 6 6 6 5 7 5 7 4 -1 -1 
Green 
2 7 3 7 3 6 4 6 4 5 5 5 5 4 6 4 6 3 7 3 7 2 -1 -1 
Blue 
0 7 0 6 1 7 1 6 1 5 2 6 2 5 2 4 3 5 3 4 3 3 4 4 4 3 -1 -1 
Skin 
0 4 0 5 1 4 1 3 2 3 2 2 3 2 3 1 4 1 4 0 5 0 -1 -1
 */
