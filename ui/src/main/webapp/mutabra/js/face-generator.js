/*
 * Copyright (c) 2008-2013 Ivan Khalopik.
 * All rights reserved.
 */

!function ($) {

    /* FACE GENERATOR CLASS DEFINITION
     * ========================= */
    var FaceGenerator = function (element) {
        this.$element = $(element);

        this.value = this.$element.data('value');
        this.$picture = this.$element.find('svg > g');

        var targetSelector = this.$element.data('target');
        var inputSelector = this.$element.data('input');
        this.$target = targetSelector && $(targetSelector);
        this.$input = inputSelector && $(inputSelector);
    };

    FaceGenerator.prototype = {
        constructor: FaceGenerator,

        show: function () {
            // redraw face with new part
            this.$target && this.$target.empty().append(this.$picture.children().clone());
            // save chosen value
            this.$input && this.$input.val(this.value);

            // activate new menu item
            this.$element.siblings('.active').removeClass('active');
            this.$element.addClass('active');
        }
    };

    /* FACE GENERATOR PLUGIN DEFINITION
     * ========================== */
    $.fn.faceGenerator = function (option) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('faceGenerator');
            if (!data) $this.data('faceGenerator', (data = new FaceGenerator(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceGenerator.Constructor = FaceGenerator;


    /* FACE TITLE CLASS DEFINITION
     * ========================= */
    var FaceTitle = function (element) {
        this.$element = $(element);

        var targetSelector = this.$element.data('target');
        var inputSelector = this.$element.data('input');
        this.$target = targetSelector && $(targetSelector);
        this.$input = inputSelector && $(inputSelector);
    };

    FaceTitle.prototype = {
        constructor: FaceTitle,

        refresh: function () {
            var value = this.$element.val();

            // redraw face with new title
            this.$target && this.$target.find('text').text(value);
            // save chosen value
            this.$input && this.$input.val(value);
        }
    };

    /* FACE TITLE PLUGIN DEFINITION
     * ========================== */
    $.fn.faceTitle = function (option) {
        return this.each(function () {
            var $this = $(this),
                data = $this.data('faceTitle');
            if (!data) $this.data('faceTitle', (data = new FaceTitle(this)));
            if (typeof option == 'string') data[option]();
        });
    };

    $.fn.faceTitle.Constructor = FaceTitle;

    /* DATA-API
     * ============== */
    $(function () {
        $(document)
            .on('click.mutabra.data-api', '[data-toggle=face]', function () {
                var $this = $(this);
                var $selected = $this.closest('[data-toggle=face]') || $this;

                $selected.faceGenerator('show');
            })
            .on('change.mutabra.data-api', '[data-toggle=face-title]', function () {
                $(this).faceTitle('refresh');
            })
    });

}(window.jQuery);