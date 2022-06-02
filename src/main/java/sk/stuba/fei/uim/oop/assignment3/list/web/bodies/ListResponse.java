package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ListResponse {

    private final Long id;

    private final List<BookResponse> lendingList;

    private final boolean lended;

    public ListResponse(LendingList list) {
        this.id = list.getId();
        this.lendingList = list.getList() == null ? null : list.getList().stream().map(BookResponse::new).collect(Collectors.toList());
        this.lended = list.isLended();
    }
}
