import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RiderDetailComponent } from './rider-detail.component';

describe('Rider Management Detail Component', () => {
  let comp: RiderDetailComponent;
  let fixture: ComponentFixture<RiderDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RiderDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ rider: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RiderDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RiderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load rider on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.rider).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
