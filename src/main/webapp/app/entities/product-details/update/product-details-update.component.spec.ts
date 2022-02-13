import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductDetailsService } from '../service/product-details.service';
import { IProductDetails, ProductDetails } from '../product-details.model';
import { IProductType } from 'app/entities/product-type/product-type.model';
import { ProductTypeService } from 'app/entities/product-type/service/product-type.service';

import { ProductDetailsUpdateComponent } from './product-details-update.component';

describe('ProductDetails Management Update Component', () => {
  let comp: ProductDetailsUpdateComponent;
  let fixture: ComponentFixture<ProductDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productDetailsService: ProductDetailsService;
  let productTypeService: ProductTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductDetailsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProductDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productDetailsService = TestBed.inject(ProductDetailsService);
    productTypeService = TestBed.inject(ProductTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call productType query and add missing value', () => {
      const productDetails: IProductDetails = { id: 456 };
      const productType: IProductType = { id: 89848 };
      productDetails.productType = productType;

      const productTypeCollection: IProductType[] = [{ id: 90005 }];
      jest.spyOn(productTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: productTypeCollection })));
      const expectedCollection: IProductType[] = [productType, ...productTypeCollection];
      jest.spyOn(productTypeService, 'addProductTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productDetails });
      comp.ngOnInit();

      expect(productTypeService.query).toHaveBeenCalled();
      expect(productTypeService.addProductTypeToCollectionIfMissing).toHaveBeenCalledWith(productTypeCollection, productType);
      expect(comp.productTypesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productDetails: IProductDetails = { id: 456 };
      const productType: IProductType = { id: 16004 };
      productDetails.productType = productType;

      activatedRoute.data = of({ productDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(productDetails));
      expect(comp.productTypesCollection).toContain(productType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductDetails>>();
      const productDetails = { id: 123 };
      jest.spyOn(productDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(productDetailsService.update).toHaveBeenCalledWith(productDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductDetails>>();
      const productDetails = new ProductDetails();
      jest.spyOn(productDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productDetails }));
      saveSubject.complete();

      // THEN
      expect(productDetailsService.create).toHaveBeenCalledWith(productDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ProductDetails>>();
      const productDetails = { id: 123 };
      jest.spyOn(productDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productDetailsService.update).toHaveBeenCalledWith(productDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductTypeById', () => {
      it('Should return tracked ProductType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
