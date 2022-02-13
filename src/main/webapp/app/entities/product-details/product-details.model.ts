import { IProductType } from 'app/entities/product-type/product-type.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IProductDetails {
  id?: number;
  details?: string | null;
  featurs?: string | null;
  activationDate?: string;
  lastModified?: string;
  lastModifiedBy?: string;
  productType?: IProductType | null;
  product?: IProduct | null;
}

export class ProductDetails implements IProductDetails {
  constructor(
    public id?: number,
    public details?: string | null,
    public featurs?: string | null,
    public activationDate?: string,
    public lastModified?: string,
    public lastModifiedBy?: string,
    public productType?: IProductType | null,
    public product?: IProduct | null
  ) {}
}

export function getProductDetailsIdentifier(productDetails: IProductDetails): number | undefined {
  return productDetails.id;
}
