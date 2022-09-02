package com.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Content {
    private String contentId; // 게시판 번호
    private String userEmail; // 게시판 작성자
    private String title; // 게시판 제목
    private String createdAt; // 게시판 작성일
    private String updatedAt; // 게시판 수정일
    private String context; // 게시글 내용

    private String commentId; // 댓글 번호
    private String commentEmail; // 댓글 작성자
    private String commentContext; // 댓글 내용
    private String commentCreatedAt; // 댓글 작성일
    private String commentUpdatedAt; // 댓글 수정일

    private String subCommentId; // 대댓글 번호
    private String subCommentEmail; // 대댓글 작성자
    private String subCommentContext; // 대댓글 내용
    private String subCommentCreatedAt; // 대댓글 작성일
    private String subCommentUpdatedAt; // 대댓글 수정일

    private String fileName; // 파일 이름
    private String fileSize; // 파일 사이즈
    private String UUID; // uuid
}
