package com.ntb.hackertonntb.dto;


import com.ntb.hackertonntb.domain.entity.Category;
import com.ntb.hackertonntb.domain.entity.SmallCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SmallCategoryDto {
        private int id;

        @NotBlank(message = "세부 카테고리는 필수 선택 값 입니다.")
        private String smallcategoryname;

        private Category categories;

        public SmallCategory toEntity(){
            SmallCategory build = SmallCategory.builder()
                    .id(id)
                    .smallcategoryname(smallcategoryname)
                    .build();
            return build;
        }
        @Builder
        public SmallCategoryDto(int id, String smallcategoryname, Category categories){
            this.id = id;
            this.smallcategoryname = smallcategoryname;
            this.categories = categories;
        }
    }
