package grid2d;

public class Backtracking {

    public static void main(String[] args) {
        int[][] labirin = {
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, 0, -1, 0, -1, -1, -1, -1, -1, -1, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2},
            {-1, -1, -1, 0, -1, -1, -1, -1, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, 0, 0, -1, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, -1, -1, 0, 0, -1},
            {-1, 1, 0, 0, -1, -1, -1, -1, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
        };

        System.out.println("");
        for (int i = 0; i < labirin.length; i++) {
            for (int j = 0; j < labirin[i].length; j++) {
                System.out.print(labirin[i][j] + ", ");
            }
            System.out.println("");
        }

        //Inisialisasi Array Arah
        //ARAH = 0 --> NORTH(Utara)
        //ARAH = 1 --> EAST(TIMUR)
        //ARAH = 2 --> SOUTH(SELATAN)
        //ARAH = 3 --> WEST(BARAT)
        int[][] arah = new int[labirin.length][labirin[0].length];
        for (int i = 0; i < arah.length; i++) {
            for (int j = 0; j < arah[i].length; j++) {
                arah[i][j] = -1;
            }
        }
        Stack<Posisi> stack = new Stack<Posisi>();
        int startX = 8, startY = 1;
        Posisi start = new Posisi(startX, startY);
        stack.push(start);
        arah[startX][startY] = 0; //NORTH(Utara)
        while (!stack.isEmpty()) {
            Posisi top;
            top = stack.peek();
            int io = top.I;
            int jo = top.J;
            int value = labirin[io][jo];
            int nextValue = value + 1;
            int direction = arah[io][jo];

            Posisi depan = null;
            Posisi kanan = null;
            Posisi kiri = null;

            Posisi north = null;//utara
            Posisi east = null;//Timur
            Posisi south = null; //Selatan
            Posisi west = null; //Barat

            //Melakukan Pengecekan
            //NORTH(UTARA)
            int i = io - 1;
            int j = jo;
            if (i >= 0 && i < labirin.length && j >= 0 && j < labirin[io].length) {
                north = new Posisi(i, j);
            }

            //EAST(TIMUR)
            i = io;
            j = jo + 1;
            if (i >= 0 && i < labirin.length && j >= 0 && j < labirin[io].length) {
                east = new Posisi(i, j);
            }

            //SOUTH(SELATAN)
            i = io + 1;
            j = jo;
            if (i >= 0 && i < labirin.length && j >= 0 && j < labirin[io].length) {
                south = new Posisi(i, j);
            }

            //WEST(BARAT)
            i = io;
            j = jo - 1;
            if (i >= 0 && i < labirin.length && j >= 0 && j < labirin[io].length) {
                west = new Posisi(i, j);
            }

            //Inisialisai Posisi Depan,Kanan, Kiri
            switch (direction) {
                case 0:
                    kiri = west;
                    depan = north;
                    kanan = east;
                    break;

                case 1:
                    kiri = north;
                    depan = east;
                    kanan = south;
                    break;

                case 2:
                    kiri = east;
                    depan = south;
                    kanan = west;
                    break;

                case 3:
                    kiri = south;
                    depan = west;
                    kanan = north;
                default:
                    break;
            }

            //CEK FINISH
            if (depan != null && labirin[depan.I][depan.J] == -2) {
                System.out.println("Jumlah Langkah = " + value);
                System.out.println("FINISH");
                break;
            }
            if (kanan != null && labirin[kanan.I][kanan.J] == -2) {
                System.out.println("Jumlah Langkah = " + value);
                System.out.println("FINISH");
                break;
            }
            if (kiri != null && labirin[kiri.I][kiri.J] == -2) {
                System.out.println("Jumlah Langkah = " + value);
                System.out.println("FINISH");
                break;
            }

            //Jika Belum Finish Maka
            //Cek Front; Right; Left
            if (depan != null && labirin[depan.I][depan.J] == 0) {
                //Bergerak Maju
                labirin[depan.I][depan.J] = nextValue;
                int d = direction;
                arah[depan.I][depan.J] = d;
                stack.push(depan);

            } else if (kanan != null && labirin[kanan.I][kanan.J] == 0) {
                labirin[kanan.I][kanan.J] = nextValue;
                int d = (direction + 1) % 4;
                arah[kanan.I][kanan.J] = d;
                stack.push(kanan);

            } else if (kiri != null && labirin[kiri.I][kiri.J] == 0) {
                labirin[kiri.I][kiri.J] = nextValue;
                int d = (direction - 1) % 4;
                if (d < 0) {
                    d = 4 + d;
                }
                arah[kiri.I][kiri.J] = d;
                stack.push(kiri);
            } else {
                //Back Menggunkana operasi POP DAN STACK
                labirin[io][jo] = -3;
                try {
                    stack.pop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            //Cetak Stack
            System.out.print("[");
            if (!stack.isEmpty()) {
                Object[] path = stack.getElements();
                for (int p = 0; p < path.length; p++) {
                    if (p > 0) {
                        System.out.print(",");
                    }
                    Posisi pos = (Posisi) path[p];
                    System.out.print(" (" + pos.I + "," + pos.J + ")");
                }
            }
            System.out.println(" ]");
        }

        System.out.println("");
        for (int i = 0; i < labirin.length; i++) {
            for (int j = 0; j < labirin[i].length; j++) {
                System.out.print(labirin[i][j] + ", ");
            }
            System.out.println("");
        }
    }

}
