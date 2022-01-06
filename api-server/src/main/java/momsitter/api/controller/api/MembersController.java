package momsitter.api.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import momsitter.core.dto.members.MembersDto;
import momsitter.core.service.members.MembersService;
import momsitter.support.dto.ApiResponse;
import momsitter.support.dto.ApiResponseGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("맘시터서비스 - 시터회원 API")
@RestController("momsitter_members")
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MembersController {

  private final MembersService membersService;

  @ApiOperation("전체조회 ")
  @GetMapping("/")
  public @ResponseBody  ApiResponse<List<MembersDto>> selectParentsInfo() {
    return ApiResponseGenerator.success(membersService.findAllData());
  }
}
