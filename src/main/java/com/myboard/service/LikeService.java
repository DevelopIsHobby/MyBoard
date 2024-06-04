package com.myboard.service;

import com.myboard.dto.LikeRequestDTO;

public interface LikeService {
    public void insert(LikeRequestDTO likeRequestDTO) throws Exception;

    public void delete(LikeRequestDTO likeRequestDTO) throws Exception;
}
