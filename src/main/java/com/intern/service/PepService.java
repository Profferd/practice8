package com.intern.service;

import com.intern.dto.PageDto;
import com.intern.dto.PepInfoDto;
import com.intern.dto.PepQueryDto;
import com.intern.dto.PepTopNamesDto;
import java.io.File;
import java.util.List;

public interface PepService {
    void upload(File file);
    PageDto<PepInfoDto> search(PepQueryDto query);
    List<PepTopNamesDto> getTopTenNames();
}
