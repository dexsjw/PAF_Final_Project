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