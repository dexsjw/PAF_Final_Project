export interface CreditCard {
    cardNum: string,
    cardExpMth: number,
    cardExpYear: number,
    cardCvc: string
}

export interface Product extends CreditCard {
    prodName: string,
    prodDesc: string,
    unitAmount: number,
    interval: string
}

export interface Status {
    teleUserId: number,
    subId: string,
    subStatus: string,
    invoiceStatus: string,
    payIntStatus: string
}