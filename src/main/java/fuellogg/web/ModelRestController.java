package fuellogg.web;

import fuellogg.model.view.ModelViewModel;
import fuellogg.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "models", description = "REST API for Models", tags = { "models" })
@RequestMapping("/api/model")
public class ModelRestController {

    private final ModelService modelService;

    @Autowired
    public ModelRestController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/{brandName}")
    @ApiOperation(value = "Find all models", notes = "Find all models ", response = ModelViewModel.class)
    public ResponseEntity<List<ModelViewModel>> getModels(@PathVariable String brandName){
        List<ModelViewModel> allByBrandName = this.modelService.getAllByBrandName(brandName);
        return ResponseEntity.ok(allByBrandName);
    }
}
