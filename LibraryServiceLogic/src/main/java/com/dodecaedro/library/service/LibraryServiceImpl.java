package com.dodecaedro.library.service;

import com.dodecaedro.library.data.pojo.Book;
import com.dodecaedro.library.data.pojo.Borrow;
import com.dodecaedro.library.data.pojo.Fine;
import com.dodecaedro.library.data.pojo.User;
import com.dodecaedro.library.exception.ActiveFinesException;
import com.dodecaedro.library.exception.BorrowMaximumLimitException;
import com.dodecaedro.library.exception.BorrowNotFoundException;
import com.dodecaedro.library.exception.ExpiredBorrowException;
import com.dodecaedro.library.repository.BorrowRepository;
import com.dodecaedro.library.repository.FineRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.dodecaedro.library.search.BorrowSpecifications.activeBorrows;
import static com.dodecaedro.library.search.BorrowSpecifications.expiredBorrows;

@Service
public class LibraryServiceImpl implements LibraryService {

  @Value("${borrow.maximum}")
  private int maximumBorrows;

  @Inject
  private BorrowRepository borrowRepository;

  @Inject
  private FineRepository fineRepository;

  @Override
  @Transactional
  public Borrow borrowBook(User user, Book book) throws ExpiredBorrowException, ActiveFinesException, BorrowMaximumLimitException {
    Objects.requireNonNull(user, "user cannot be null");
    Objects.requireNonNull(user.getUserId(), "user id cannot be null");
    Objects.requireNonNull(book, "book cannot be null");
    Objects.requireNonNull(book.getBookId(), "book id cannot be null");

    ZonedDateTime nowDate = ZonedDateTime.now();

    if (!fineRepository.findActiveFinesInDate(user, nowDate).isEmpty()) {
      throw new ActiveFinesException("The user has running fines");
    }

    if (borrowRepository.count(expiredBorrows(user.getUserId())) > 0) {
      throw new ExpiredBorrowException("cannot borrow new books because the user has expired borrows");
    }

    if (maximumBorrows <= borrowRepository.count(activeBorrows(user.getUserId()))) {
      throw new BorrowMaximumLimitException("User has already reached the maximum number of simultaneous borrows");
    }

    Borrow borrow = new Borrow();
    borrow.setBookId(book.getBookId());
    borrow.setUserId(user.getUserId());
    borrow.setBook(book);
    borrow.setUser(user);

    borrow.setBorrowDate(nowDate);
    borrow.setExpectedReturnDate(nowDate.plusWeeks(2));

    borrowRepository.save(borrow);

    return borrow;
  }

  @Override
  @Transactional
  public Borrow returnBook(User user, Book book) throws BorrowNotFoundException {
    Objects.requireNonNull(user, "user cannot be null");
    Objects.requireNonNull(user.getUserId(), "user id cannot be null");
    Objects.requireNonNull(book, "book cannot be null");
    Objects.requireNonNull(book.getBookId(), "book id cannot be null");

    Borrow borrow = borrowRepository
      .findTopByUserAndBookAndActualReturnDateIsNullOrderByBorrowDateDesc(user, book)
      .orElseThrow(() -> new BorrowNotFoundException("No unreturned borrow found for the user and book"));

    ZonedDateTime nowDate = ZonedDateTime.now();
    borrow.setActualReturnDate(nowDate);

    Duration fineDuration = Duration.between(borrow.getExpectedReturnDate(), nowDate);
    if (!fineDuration.isZero()) {
      Fine fine = new Fine();
      fine.setUser(user);
      fine.setUserId(user.getUserId());
      fine.setFineStartDate(nowDate);
      fine.setFineEndDate(nowDate.plus(fineDuration));
      fineRepository.save(fine);
    }

    borrowRepository.save(borrow);

    return borrow;
  }

  @Override
  public Optional<Borrow> findActiveBorrow(User user, Book book) {
    return borrowRepository.findTopByUserAndBookAndActualReturnDateIsNullOrderByBorrowDateDesc(user, book);
  }
}
