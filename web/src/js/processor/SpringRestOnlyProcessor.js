import $ from "jquery";

export class SpringRestOnlyProcessor {

    #$squareArea;
    #squares;

    constructor($squareArea) {
        this.#$squareArea = $squareArea;
    }

    process() {
        let $defer = $.Deferred();

        this.#squares = this.#$squareArea.children('.square').get();

        console.debug('SpringRestOnlyProcessor start...');
        this.#processNext().always(() => {
            $defer.resolve();
        });

        return $defer.promise();
    }

    #processNext() {
        let $defer = $.Deferred();

        let requestSquare = this.#squares.shift();

        if (!requestSquare) {
            return $defer.resolve().promise();
        }

        let $requestSquare = $(requestSquare);

        $requestSquare.addClass('processing');

        $.post('http://localhost:8080/time-registries', () => {
            $requestSquare.removeClass('processing').addClass('success');
        }).fail(() => {
            $requestSquare.removeClass('processing').addClass('fail');
        }).always(() => {
            this.#processNext().always(() => {
                $defer.resolve();
            });
        });;

        return $defer.promise();
    }
}