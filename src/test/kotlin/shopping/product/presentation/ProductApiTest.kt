package shopping.product.presentation

import io.kotest.datatest.withData
import io.mockk.every
import io.mockk.justRun
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import shopping.product.fixture.ProductFixture
import shopping.support.KotestControllerTestSupport

class ProductApiTest : KotestControllerTestSupport() {

    init {
        Given("상품 등록 요청이 왔을 때") {
            When("정상 적인 상품 정보를 받은 경우") {
                every { productCommandService.createProduct(ProductFixture.`상품 1`.`상품 생성 COMMAND 생성`()) } returns ProductFixture.`상품 1`.`상품 엔티티 생성`()

                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 1`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())

                Then("201 상태 코드를 반환 한다") {
                    response.isStatusAs(HttpStatus.CREATED)
                }

                Then("등록 된 상품의 ID 를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber)
                }
            }

            When("상품 판매 가격이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 NULL 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격을 입력해주세요.")
                }
            }

            When("상품 판매 가격이 최대 범위를 넘긴 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 최대값 초과 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격은 최대 999,999,999 입니다.")
                }
            }

            When("상품 판매 가격이 최소 범위 미만인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 최소값 미만 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격은 음수일 수 없습니다.")
                }
            }

            When("상품 정가 값이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 NULL 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가를 입력해주세요.")
                }
            }

            When("상품 정가 값이 최대 범위를 넘긴 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 최대값 초과 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가는 최대 999,999,999 입니다.")
                }
            }

            When("상품 정가 값이 최소 범위 미만인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 최소값 미만 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가는 음수일 수 없습니다.")
                }
            }

            When("상품 이름이 공백인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 공백 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름을 입력해주세요.")
                }
            }

            When("상품 이름이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 공백 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름을 입력해주세요.")
                }
            }

            When("상품 이름의 길이가 최대 길이를 초과 한 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 최대 길이 초과 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름은 최대 15자 까지 입력할 수 있습니다.")
                }
            }

            withData(
                nameFn = { "허용 특수 문자 : $it" },
                listOf(
                    ProductFixture.`상품 이름 허용 특수 문자 상품1`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품2`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품3`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품4`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품5`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품6`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품7`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품8`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품9`,
                )
            ) {
                When("상품 이름에 허용된 특수 문자가 포함 된 경우") {
                    every { productCommandService.createProduct(it.`상품 생성 COMMAND 생성`()) } returns it.`상품 엔티티 생성`()

                    val response =
                        mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/products")
                                .content(objectMapper.writeValueAsBytes(it.`상품 등록 요청 DTO 생성`()))
                                .contentType(MediaType.APPLICATION_JSON),
                        ).andDo(MockMvcResultHandlers.print())

                    Then("201 상태 코드를 반환 한다") {
                        response.isStatusAs(HttpStatus.CREATED)
                    }

                    Then("등록 된 상품의 ID 를 반환 한다") {
                        response.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").isNumber)
                    }
                }
            }

            When("상품 이름에 허용되지 않는 특수 문자가 포함 된 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 허용되지 않은 특수 문자 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름은 한글, 영어, 숫자, 허용 된 특수문자((, ), [, ], +, -, &, /, _) 사용 가능합니다.")
                }
            }

            When("상품 재고가 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 재고 NULL 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 재고를 입력해주세요.")
                }
            }

            When("상품 재고가 음수인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 재고 음수 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 개수는 음수일 수 없습니다.")
                }
            }

            When("상품 이미지 경로가 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/products")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이미지 NULL 상품`.`상품 등록 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이미지를 등록해주세요.")
                }
            }
        }

        Given("상품 수정 요청이 왔을 때") {
            When("정상 적인 상품 정보를 받은 경우") {
                justRun { productCommandService.modifyProduct(any(), ProductFixture.`상품 1`.`상품 수정 COMMAND 생성`()) }

                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 1`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())

                Then("204 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isNoContent)
                }

                Then("등록 된 상품의 ID 를 반환 한다") {
                    response.andExpect(content().string(""))
                }
            }

            When("상품 판매 가격이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 NULL 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격을 입력해주세요.")
                }
            }

            When("상품 판매 가격이 최대 범위를 넘긴 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 최대값 초과 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격은 최대 999,999,999 입니다.")
                }
            }

            When("상품 판매 가격이 최소 범위 미만인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 판매 가격 최소값 미만 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 판매 가격은 음수일 수 없습니다.")
                }
            }

            When("상품 정가 값이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 NULL 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가를 입력해주세요.")
                }
            }

            When("상품 정가 값이 최대 범위를 넘긴 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 최대값 초과 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가는 최대 999,999,999 입니다.")
                }
            }

            When("상품 정가 값이 최소 범위 미만인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 정가 최소값 미만 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 가격 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 정가는 음수일 수 없습니다.")
                }
            }

            When("상품 이름이 공백인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 공백 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름을 입력해주세요.")
                }
            }

            When("상품 이름이 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 공백 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름을 입력해주세요.")
                }
            }

            When("상품 이름의 길이가 최대 길이를 초과 한 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 최대 길이 초과 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름은 최대 15자 까지 입력할 수 있습니다.")
                }
            }

            withData(
                nameFn = { "허용 특수 문자 : $it" },
                listOf(
                    ProductFixture.`상품 이름 허용 특수 문자 상품1`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품2`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품3`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품4`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품5`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품6`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품7`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품8`,
                    ProductFixture.`상품 이름 허용 특수 문자 상품9`,
                )
            ) {
                When("상품 이름에 허용된 특수 문자가 포함 된 경우") {
                    justRun { productCommandService.modifyProduct(any(), it.`상품 수정 COMMAND 생성`()) }

                    val response =
                        mockMvc.perform(
                            MockMvcRequestBuilders.put("/api/products/1")
                                .content(objectMapper.writeValueAsBytes(it.`상품 수정 요청 DTO 생성`()))
                                .contentType(MediaType.APPLICATION_JSON),
                        ).andDo(MockMvcResultHandlers.print())

                    Then("204 상태 코드를 반환 한다") {
                        response.andExpect(MockMvcResultMatchers.status().isNoContent)
                    }

                    Then("응답 바디는 공백이다") {
                        response.andExpect(content().string(""))
                    }
                }
            }

            When("상품 이름에 허용되지 않는 특수 문자가 포함 된 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이름 허용되지 않은 특수 문자 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이름은 한글, 영어, 숫자, 허용 된 특수문자((, ), [, ], +, -, &, /, _) 사용 가능합니다.")
                }
            }

            When("상품 재고가 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 재고 NULL 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 재고를 입력해주세요.")
                }
            }

            When("상품 재고가 음수인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 재고 음수 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 개수는 음수일 수 없습니다.")
                }
            }

            When("상품 이미지 경로가 null 인 경우") {
                val response =
                    mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/products/1")
                            .content(objectMapper.writeValueAsBytes(ProductFixture.`상품 이미지 NULL 상품`.`상품 수정 요청 DTO 생성`()))
                            .contentType(MediaType.APPLICATION_JSON),
                    ).andDo(MockMvcResultHandlers.print())


                Then("400 상태 코드를 반환 한다") {
                    response.andExpect(MockMvcResultMatchers.status().isBadRequest)
                }

                Then("상품 이름 에러 메시지를 반환 한다") {
                    response.isInvalidInputValueResponse("상품 이미지를 등록해주세요.")
                }
            }
        }
    }

}
