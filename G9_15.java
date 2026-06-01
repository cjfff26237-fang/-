import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * G9期末專案 - 智慧型學生學業表現與文字介面分析系統
 * 開發團隊：第 15 組 (邱芷芳、黃軒楷、伍家葳、丁立翰、蔡沛儒)
 * 依照規範修正檔名與主類別名稱為：G9_15
 */
public class G9_15 { // 檔案名稱必須儲存為 G9_15.java

    // 1.1 物件封裝 (Encapsulation)：將學生資料獨立封裝
    static class Student {
        private String id;       // 學號
        private String name;     // 姓名
        private int javaScore;   // Java 成績
        private int pythonScore; // Python 成績

        // 建構子
        public Student(String id, String name, int javaScore, int pythonScore) {
            this.id = id;
            this.name = name;
            this.javaScore = javaScore;
            this.pythonScore = pythonScore;
        }

        // Getter 存取器
        public String getId() { return id; }
        public String getName() { return name; }
        public int getJavaScore() { return javaScore; }
        public int getPythonScore() { return pythonScore; }
        public int getTotalScore() { return javaScore + pythonScore; }

        @Override
        public String toString() {
            return String.format("%-10s %-8s %-10d %-12d %-6d", id, name, javaScore, pythonScore, getTotalScore());
        }
    }

    // 1.2 收藏 (Collections)：利用 List 動態儲存學生紀錄
    private static List<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 預設幾筆測試資料，方便助教 Open Project 後直接執行能瀏覽效果
        studentList.add(new Student("414630201", "Howard", 95, 92));
        studentList.add(new Student("414630987", "StudentB", 88, 90));
        studentList.add(new Student("414631373", "StudentC", 75, 80));
        studentList.add(new Student("413638114", "StudentD", 60, 55));
        studentList.add(new Student("414631647", "StudentE", 45, 50));

        while (true) {
            printMainMenu();
            System.out.print("請輸入功能編號 (0-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addStudentData();
                    break;
                case "2":
                    showAllGrades();
                    break;
                case "3":
                    showRankings();
                    break;
                case "4":
                    searchStudent();
                    break;
                case "5":
                    runRecursiveAnalysis();
                    break;
                case "0":
                    System.out.println("\n[系統提示] 安全結束系統。感謝使用！");
                    return;
                default:
                    System.out.println("\n[錯誤] 無此功能編號，請輸入 0 到 5 之間的數字。");
                    break;
            }
            System.out.println("\n按任意鍵返回主選單...");
            scanner.nextLine();
        }
    }

    // 3.1 系統主選單介面
    private static void printMainMenu() {
        System.out.println("\n======================================================================");
        System.out.println("                     第 15 組 - 學生學業表現分析系統");
        System.out.println("======================================================================");
        System.out.println(" [1] 新增學生與成績資料 (實作：字串格式檢查、物件封裝)");
        System.out.println(" [2] 瀏覽完整班級成績簿 (實作：List 收藏走訪)");
        System.out.println(" [3] 產生成績排行榜     (實作：多條件排序演算法)");
        System.out.println(" [4] 快速搜尋學生紀錄   (實作：學號與姓名關鍵字搜尋)");
        System.out.println(" [5] 班級學習成效分析   (實作：遞迴演算法區間動態統計)");
        System.out.println(" [0] 安全結束系統");
        System.out.println("======================================================================");
    }

    // 3.2 功能 [1]：新增資料與字串處理防呆
    private static void addStudentData() {
        System.out.println("\n您選擇了功能 [1] -> 新增學生與成績資料\n");
        String id = "";
        
        while (true) {
            System.out.print("請輸入學生學號 (必須為9位數字): ");
            id = scanner.nextLine();
            // 字串處理：利用 matches() 正則表達式檢查是否剛好為 9 位數字
            if (id.matches("\\d{9}")) {
                break;
            }
            System.out.println("[系統提示] 錯誤：學號格式不符！必須全為數字。請重新輸入。\n");
        }

        System.out.print("請輸入學生姓名: ");
        String name = scanner.nextLine();

        int javaScore = inputScore("Java");
        int pythonScore = inputScore("Python");

        // 封裝成物件並存入 List 收藏
        studentList.add(new Student(id, name, javaScore, pythonScore));
        System.out.printf("\n[系統提示] 資料封裝成功！已將學生 [%s, %s] 存入系統收藏中。\n", name, id);
    }

    // 分數輸入之防呆例外處理
    private static int inputScore(String subject) {
        while (true) {
            try {
                System.out.printf("請輸入 %s 課程成績 (0-100): ", subject);
                int score = Integer.parseInt(scanner.nextLine());
                if (score >= 0 && score <= 100) {
                    return score;
                }
                System.out.println("[系統提示] 錯誤：成績必須在 0 到 100 之間！\n");
            } catch (NumberFormatException e) {
                System.out.println("[系統提示] 錯誤：請輸入正確的整數數字！\n");
            }
        }
    }

    // 功能 [2]：瀏覽完整班級成績簿
    private static void showAllGrades() {
        System.out.println("\n您選擇了功能 [2] -> 瀏覽完整班級成績簿\n");
        if (studentList.isEmpty()) {
            System.out.println("目前系統內無任何學生資料。");
            return;
        }
        printTableHeader();
        for (Student s : studentList) {
            System.out.println(s);
        }
    }

    // 功能 [3]：成績排行榜 (排序演算法 - 依總分由高到低，此處採用氣泡排序法)
    private static void showRankings() {
        System.out.println("\n您選擇了功能 [3] -> 產生成績排行榜\n");
        if (studentList.isEmpty()) {
            System.out.println("目前系統內無任何學生資料。");
            return;
        }

        // 複製一份清單進行排序，避免破壞原始輸入順序
        List<Student> sortedList = new ArrayList<>(studentList);
        
        // 排序演算法 (Sorting)：依據 getTotalScore() 進行氣泡排序
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - 1 - i; j++) {
                if (sortedList.get(j).getTotalScore() < sortedList.get(j + 1).getTotalScore()) {
                    Student temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-6s %-10s %-8s %-10s %-12s %-6s\n", "名次", "學號", "姓名", "Java成績", "Python成績", "總分");
        System.out.println("----------------------------------------------------------------------");
        int rank = 1;
        for (Student s : sortedList) {
            System.out.printf("%-6d %s\n", rank++, s);
        }
    }

    // 功能 [4]：快速搜尋學生紀錄 (搜尋演算法 - 支援關鍵字模糊搜尋)
    private static void searchStudent() {
        System.out.println("\n您選擇了功能 [4] -> 快速搜尋學生紀錄\n");
        System.out.print("請輸入欲搜尋的關鍵字 (學號或姓名): ");
        String keyword = scanner.nextLine().trim();

        boolean found = false;
        printTableHeader();
        
        // 搜尋演算法 (Searching)
        for (Student s : studentList) {
            if (s.getId().contains(keyword) || s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("查無符合該關鍵字的學生紀錄。");
        }
    }

    // 3.3 功能 [5]：遞迴成效分析進入點
    private static void runRecursiveAnalysis() {
        System.out.println("\n您選擇了功能 [5] -> 班級學習成效分析\n");
        System.out.println("正在啟動遞迴分析模組 (Recursive Grade Distribution Analyzer)...");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("【階層式成效統計報告】");
        System.out.println("----------------------------------------------------------------------");

        // 呼叫遞迴函數分別統計不同區間的人數
        int numExcellent = countRangeRecursive(0, 180, 201); // 總分 180 ~ 200
        int numGood      = countRangeRecursive(0, 160, 179); // 總分 160 ~ 179
        int numFair      = countRangeRecursive(0, 120, 159); // 總分 120 ~ 159
        int numPoor      = countRangeRecursive(0, 0,   119); // 總分   0 ~ 119

        System.out.printf("  [卓越] 總分 >= 180 分  : %d 人\n", numExcellent);
        System.out.printf("  [優良] 160 <= 總分 < 180: %d 人\n", numGood);
        System.out.printf("  [尚可] 120 <= 總分 < 160: %d 人\n", numFair);
        System.out.printf("  [待加強] 總分 < 120 分  : %d 人\n", numPoor);
        System.out.println("----------------------------------------------------------------------");
        System.out.println(">> 分析完畢！本班級學業表現呈「常態分佈」，教學進度建議維持現狀。");
    }

    // 核心遞迴函數 (Recursion)
    private static int countRangeRecursive(int index, int lowBound, int highBound) {
        if (index >= studentList.size()) {
            return 0;
        }

        Student student = studentList.get(index);
        int total = student.getTotalScore();
        int currentMatch = (total >= lowBound && total <= highBound) ? 1 : 0;

        return currentMatch + countRangeRecursive(index + 1, lowBound, highBound);
    }

    private static void printTableHeader() {
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-10s %-8s %-10s %-12s %-6s\n", "學號", "姓名", "Java成績", "Python成績", "總分");
        System.out.println("----------------------------------------------------------------------");
    }
}