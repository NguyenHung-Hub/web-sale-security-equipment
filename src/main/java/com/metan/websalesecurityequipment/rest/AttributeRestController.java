package com.metan.websalesecurityequipment.rest;

import com.metan.websalesecurityequipment.model.Attribute;
import com.metan.websalesecurityequipment.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attribute")
public class AttributeRestController {

    @Autowired
    private AttributeService attributeService;

    @PostMapping(consumes = "text/plain;charset=UTF-8")
    public List<Attribute> findAttributeByCategoryId(@RequestBody String id) {
        long idConvert = Long.parseLong(id);
        return attributeService.findAttributesByCategoryCategoryId(idConvert);
    }
}
