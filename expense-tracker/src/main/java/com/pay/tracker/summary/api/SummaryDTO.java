package com.pay.tracker.summary.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SummaryDTO<S> {
    private List<Row<S>> list;
    private Integer totalCount;
    private Long totalBalance;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Row<R> {
        private R item;
        private int count;
        private long balance;
    }
}
