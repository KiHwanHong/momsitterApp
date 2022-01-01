package momsitter.api.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import momsitter.core.dto.parents.ParentsDto;
import momsitter.core.service.parents.ParentsService;
import momsitter.support.dto.ApiResponse;
import momsitter.support.dto.ApiResponseGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("맘시터서비스 - 부모회원 API")
@RestController("momsitter_parents")
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentsController {

  private final ParentsService parentsService;

  @ApiOperation("전체조회 ")
  @GetMapping("/")
  public @ResponseBody ApiResponse<ParentsDto> selectParentsInfo() {
    return ApiResponseGenerator.success(parentsService.findAll());
  }
}
