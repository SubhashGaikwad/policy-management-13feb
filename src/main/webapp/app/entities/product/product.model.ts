import { IProductDetails } from 'app/entities/product-details/product-details.model';
import { IPolicy } from 'app/entities/policy/policy.model';
import { ICompany } from 'app/entities/company/company.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  planNo?: number | null;
  uinNo?: string | null;
  lastModified?: string;
  lastModifiedBy?: string;
  productDetails?: IProductDetails | null;
  policy?: IPolicy | null;
  company?: ICompany | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string | null,
    public planNo?: number | null,
    public uinNo?: string | null,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public productDetails?: IProductDetails | null,
    public policy?: IPolicy | null,
    public company?: ICompany | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
