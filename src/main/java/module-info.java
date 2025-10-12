module com.example.fpoe_sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fpoe_sudoku to javafx.fxml;
    exports com.example.fpoe_sudoku;
}