package com.myboard.service;

import com.myboard.dto.ReviewDTO;
import com.myboard.entity.Board;
import com.myboard.entity.Member;
import com.myboard.entity.Review;
import com.myboard.repository.MemberRepository;
import com.myboard.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    @Override
    public List<ReviewDTO> getListReviewsByBoard(Long bno) {
        Board board = Board.builder().bno(bno).build();

        List<Review> result = reviewRepository.findByBoard(board);

        return result.stream().map(boardReview -> entityToDTO(boardReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);

        memberRepository.save(review.getReviewer());
        reviewRepository.save(review);

        return review.getRno();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Optional<Review> result = reviewRepository.findById(reviewDTO.getRno());

        if(result.isPresent()) {
            Review review = result.get();

            Member reviwer = review.getReviewer();

            review.changeText(reviewDTO.getText());

            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long rno) {
        reviewRepository.deleteById(rno);
    }
}
