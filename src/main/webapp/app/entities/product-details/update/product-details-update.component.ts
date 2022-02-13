import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProductDetails, ProductDetails } from '../product-details.model';
import { ProductDetailsService } from '../service/product-details.service';
import { IProductType } from 'app/entities/product-type/product-type.model';
import { ProductTypeService } from 'app/entities/product-type/service/product-type.service';

@Component({
  selector: 'jhi-product-details-update',
  templateUrl: './product-details-update.component.html',
})
export class ProductDetailsUpdateComponent implements OnInit {
  isSaving = false;

  productTypesCollection: IProductType[] = [];

  editForm = this.fb.group({
    id: [],
    details: [],
    featurs: [],
    activationDate: [null, [Validators.required]],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    productType: [],
  });

  constructor(
    protected productDetailsService: ProductDetailsService,
    protected productTypeService: ProductTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productDetails }) => {
      this.updateForm(productDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productDetails = this.createFromForm();
    if (productDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.productDetailsService.update(productDetails));
    } else {
      this.subscribeToSaveResponse(this.productDetailsService.create(productDetails));
    }
  }

  trackProductTypeById(index: number, item: IProductType): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(productDetails: IProductDetails): void {
    this.editForm.patchValue({
      id: productDetails.id,
      details: productDetails.details,
      featurs: productDetails.featurs,
      activationDate: productDetails.activationDate,
      lastModified: productDetails.lastModified,
      lastModifiedBy: productDetails.lastModifiedBy,
      productType: productDetails.productType,
    });

    this.productTypesCollection = this.productTypeService.addProductTypeToCollectionIfMissing(
      this.productTypesCollection,
      productDetails.productType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productTypeService
      .query({ 'productDetailsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProductType[]>) => res.body ?? []))
      .pipe(
        map((productTypes: IProductType[]) =>
          this.productTypeService.addProductTypeToCollectionIfMissing(productTypes, this.editForm.get('productType')!.value)
        )
      )
      .subscribe((productTypes: IProductType[]) => (this.productTypesCollection = productTypes));
  }

  protected createFromForm(): IProductDetails {
    return {
      ...new ProductDetails(),
      id: this.editForm.get(['id'])!.value,
      details: this.editForm.get(['details'])!.value,
      featurs: this.editForm.get(['featurs'])!.value,
      activationDate: this.editForm.get(['activationDate'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      productType: this.editForm.get(['productType'])!.value,
    };
  }
}
