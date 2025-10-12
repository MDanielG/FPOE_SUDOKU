module com.example.demosudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.fpoe_sudoku to javafx.fxml;
    opens com.example.fpoe_sudoku.controller to javafx.fxml;
    exports com.example.fpoe_sudoku;
}