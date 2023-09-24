import $ from "jquery";

export class SpringRestOnlyAllInOneProcessor {

    $processingArea;
    #$squareArea;
    #squares;
    #endpointUrl;

    startDate;
    endDate;
    timeSpent;

    constructor($processingArea, endpointUrl) {
        this.$processingArea = $processingArea;
        this.#$squareArea = this.$processingArea.children('.squares');
        this.#endpointUrl = endpointUrl;
    }

    async process() {
        this.startDate = new Date();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyAllInOneProcessor start...');

        await this.#processSquares();

        this.endDate = new Date();
        this.timeSpent = (this.endDate - this.startDate) / 1000;
    }

    async #processSquares() {

        this.#$squareArea.children('.square').addClass('processing');

        let jsonDataListSize = this.#squares.length;
        let jsonDataList = new Array();

        for (let i = 0; i < jsonDataListSize; i++) {
            jsonDataList.push({
                data: JSON.stringify({ storedNumer: Math.floor(Math.random() * 999999999) })
            });
        }

        await new Promise((resolve) => {
            $.post({
                url: this.#endpointUrl,
                data: JSON.stringify(jsonDataList),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: () => {
                    this.#$squareArea.children('.square').removeClass('processing').addClass('success');
                }
            }).fail(() => {
                this.#$squareArea.children('.square').removeClass('processing').addClass('fail');
            }).always(() => {
                resolve();
            });
        });
    }
}