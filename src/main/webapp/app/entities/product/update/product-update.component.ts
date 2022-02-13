import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProduct, Product } from '../product.model';
import { ProductService } from '../service/product.service';
import { IProductDetails } from 'app/entities/product-details/product-details.model';
import { ProductDetailsService } from 'app/entities/product-details/service/product-details.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;

  productDetailsCollection: IProductDetails[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    planNo: [],
    uinNo: [],
    lastModified: [null, [Validators.required]],
    lastModifiedBy: [null, [Validators.required]],
    productDetails: [],
    company: [],
  });

  constructor(
    protected productService: ProductService,
    protected productDetailsService: ProductDetailsService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  trackProductDetailsById(index: number, item: IProductDetails): number {
    return item.id!;
  }

  trackCompanyById(index: number, item: ICompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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

  protected updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      planNo: product.planNo,
      uinNo: product.uinNo,
      lastModified: product.lastModified,
      lastModifiedBy: product.lastModifiedBy,
      productDetails: product.productDetails,
      company: product.company,
    });

    this.productDetailsCollection = this.productDetailsService.addProductDetailsToCollectionIfMissing(
      this.productDetailsCollection,
      product.productDetails
    );
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing(this.companiesSharedCollection, product.company);
  }

  protected loadRelationshipsOptions(): void {
    this.productDetailsService
      .query({ 'productId.specified': 'false' })
      .pipe(map((res: HttpResponse<IProductDetails[]>) => res.body ?? []))
      .pipe(
        map((productDetails: IProductDetails[]) =>
          this.productDetailsService.addProductDetailsToCollectionIfMissing(productDetails, this.editForm.get('productDetails')!.value)
        )
      )
      .subscribe((productDetails: IProductDetails[]) => (this.productDetailsCollection = productDetails));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing(companies, this.editForm.get('company')!.value))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }

  protected createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      planNo: this.editForm.get(['planNo'])!.value,
      uinNo: this.editForm.get(['uinNo'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      productDetails: this.editForm.get(['productDetails'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }
}
