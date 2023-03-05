package com.intern.repo;

import com.intern.data.Pep;
import com.intern.dto.PepQueryDto;
import com.intern.dto.PepTopNamesDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomPepRepository {
     Page<Pep> search(PepQueryDto query);
     List<PepTopNamesDto> findTopTenNames();
}
