import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PremiunDetailsDetailComponent } from './premiun-details-detail.component';

describe('PremiunDetails Management Detail Component', () => {
  let comp: PremiunDetailsDetailComponent;
  let fixture: ComponentFixture<PremiunDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PremiunDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ premiunDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PremiunDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PremiunDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load premiunDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.premiunDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
