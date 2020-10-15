package chess.gui;

import chess.board.Move;
import chess.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final Dimension TAKEN_PIECES_DIMENSIONS = new Dimension(40, 80);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        northPanel = new JPanel(new GridLayout(8, 2));
        southPanel = new JPanel(new GridLayout(8, 2));
        northPanel.setBackground(PANEL_COLOR);
        southPanel.setBackground(PANEL_COLOR);
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSIONS);
    }

    public void redo(final Table.MoveLog moveLog) {
        southPanel.removeAll();
        northPanel.removeAll();
        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for (final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece taken = move.getAttackedPiece();
                if (taken.getPieceAlliance().isWhite()) {
                    whiteTakenPieces.add(taken);
                } else if (taken.getPieceAlliance().isBlack()) {
                    blackTakenPieces.add(taken);
                } else {
                    throw new RuntimeException("Invalid piece");
                }
            }
            Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
                @Override
                public int compare(Piece o1, Piece o2) {
                    return Integer.compare(o1.getPieceValue(), o2.getPieceValue());
                }
            });

            Collections.sort(blackTakenPieces, new Comparator<Piece>() {
                @Override
                public int compare(Piece o1, Piece o2) {
                    return Integer.compare(o1.getPieceValue(), o2.getPieceValue());
                }
            });

            for (final Piece takenPiece : whiteTakenPieces) {
                try {
                    final BufferedImage image = ImageIO.read(new File("art/pieces/plain" +
                            takenPiece.getPieceAlliance().toString().substring(0, 1) +
                            "" + takenPiece.toString()));
                    final ImageIcon icon = new ImageIcon(image);
                    final JLabel imageLabel = new JLabel();
                    this.southPanel.add(imageLabel);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }


            for (final Piece takenPiece : blackTakenPieces) {
                try {
                    final BufferedImage image = ImageIO.read(new File("art/pieces/plain" +
                            takenPiece.getPieceAlliance().toString().substring(0, 1) +
                            "" + takenPiece.toString()));
                    final ImageIcon icon = new ImageIcon(image);
                    final JLabel imageLabel = new JLabel();
                    this.southPanel.add(imageLabel);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        validate();
    }
}
