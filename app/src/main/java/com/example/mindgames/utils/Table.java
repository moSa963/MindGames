package com.example.mindgames.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class Table {
    private int width;
    private int height;
    private final Context context;
    private TableLayout table;
    private ArrayList<FrameLayout> cells;
    private boolean cellClickable;


    public Table(Context context, int width, int height){
        this.width = 0;
        this.height = 0;
        this.context = context;
        this.table = null;
        this.cells = new ArrayList<>();
    }

    public final void createNewTable(int width, int height, int margins, boolean cellClickble, int backRes){
        this.width = width;
        this.height = height;
        this.table = null;
        this.cells = new ArrayList<>();
        this.cellClickable = cellClickble;
        table = createTable(backRes, margins);
    }

    public final void createNewTable(int width, int height, int margins, boolean cellClickable){
        this.width = width;
        this.height = height;
        this.table = null;
        this.cells = new ArrayList<>();
        this.cellClickable = cellClickable;
        table = createTable(-1, margins);
    }

    private TableLayout createTable(int backRes, int margins){
        TableLayout table = new TableLayout(context);

        for(int i = 0; i < height; ++i){
            TableRow row = createRow(backRes, margins);
            table.addView(row);
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
        );

        table.setLayoutParams(params);

        return table;
    }

    private TableRow createRow(int backRes, int margins){
        TableRow newRow = new TableRow(context);

        for(int i = 0; i < width; ++i){
            FrameLayout cell = createCell(cells.size(), backRes, margins);
            cells.add(cell);
            newRow.addView(cell);
        }

        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1
        );

        newRow.setLayoutParams(params);
        return newRow;
    }

    private FrameLayout createCell(int index, int backRes, int margins){
        FrameLayout cell = new FrameLayout(context);

        if (backRes != -1)
            cell.setBackgroundResource(backRes);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        params.setMargins(margins, margins, margins, margins);

        params.weight = 1;
        cell.setLayoutParams(params);

        if (cellClickable){
            cell.setOnClickListener(x->{
                onClickCell((FrameLayout) x, index);
            });
        }else{
            cell.setClickable(false);
        }

        return cell;
    }


    public void onClickCell(ViewGroup cell, int index) {}

    public final FrameLayout cellAt(int y, int x){
        int index = (y * width) + x;
        return cellAt(index);
    }

    public final FrameLayout cellAt(int index){
        return cells.get(index);
    }

    public final TableLayout getTable(){
        return table;
    }

    public final Context getContext() {
        return context;
    }
}
