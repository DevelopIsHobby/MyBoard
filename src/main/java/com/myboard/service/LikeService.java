package com.myboard.service;

import com.myboard.dto.LikeRequestDTO;

public interface LikeService {
    void insert(LikeRequestDTO likeRequestDTO) throws Exception;

    void delete(LikeRequestDTO likeRequestDTO) throws Exception;
}
