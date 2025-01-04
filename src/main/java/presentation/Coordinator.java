package presentation;

import data.contracts.ResultContract;
import data.contracts.SelectContract;

public class Coordinator {
    private final SelectContract.Presenter selectPresenter;
    private final ResultContract.Presenter resultPresenter;

    public Coordinator(SelectContract.Presenter selectPresenter, ResultContract.Presenter resultPresenter) {
        this.selectPresenter = selectPresenter;
        this.resultPresenter = resultPresenter;

        setupInteractions();
    }

    private void setupInteractions() {
        selectPresenter.setDataUpdatedListener(() -> {
            String tsv = selectPresenter.getController().getResultsAsTsv();
            resultPresenter.updateResultTable(tsv);
        });
    }
}