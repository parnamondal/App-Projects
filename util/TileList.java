package android.support.p003v7.util;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* renamed from: android.support.v7.util.TileList */
class TileList<T> {
    Tile<T> mLastAccessedTile;
    final int mTileSize;
    private final SparseArray<Tile<T>> mTiles = new SparseArray<>(10);

    /* renamed from: android.support.v7.util.TileList$Tile */
    public static class Tile<T> {
        public int mItemCount;
        public final T[] mItems;
        Tile<T> mNext;
        public int mStartPosition;

        public Tile(Class<T> klass, int size) {
            this.mItems = (Object[]) Array.newInstance(klass, size);
        }

        /* access modifiers changed from: 0000 */
        public boolean containsPosition(int pos) {
            int i = this.mStartPosition;
            return i <= pos && pos < i + this.mItemCount;
        }

        /* access modifiers changed from: 0000 */
        public T getByPosition(int pos) {
            return this.mItems[pos - this.mStartPosition];
        }
    }

    public TileList(int tileSize) {
        this.mTileSize = tileSize;
    }

    public T getItemAt(int pos) {
        Tile<T> tile = this.mLastAccessedTile;
        if (tile == null || !tile.containsPosition(pos)) {
            int index = this.mTiles.indexOfKey(pos - (pos % this.mTileSize));
            if (index < 0) {
                return null;
            }
            this.mLastAccessedTile = (Tile) this.mTiles.valueAt(index);
        }
        return this.mLastAccessedTile.getByPosition(pos);
    }

    public int size() {
        return this.mTiles.size();
    }

    public void clear() {
        this.mTiles.clear();
    }

    public Tile<T> getAtIndex(int index) {
        return (Tile) this.mTiles.valueAt(index);
    }

    public Tile<T> addOrReplace(Tile<T> newTile) {
        int index = this.mTiles.indexOfKey(newTile.mStartPosition);
        if (index < 0) {
            this.mTiles.put(newTile.mStartPosition, newTile);
            return null;
        }
        Tile<T> oldTile = (Tile) this.mTiles.valueAt(index);
        this.mTiles.setValueAt(index, newTile);
        if (this.mLastAccessedTile == oldTile) {
            this.mLastAccessedTile = newTile;
        }
        return oldTile;
    }

    public Tile<T> removeAtPos(int startPosition) {
        Tile<T> tile = (Tile) this.mTiles.get(startPosition);
        if (this.mLastAccessedTile == tile) {
            this.mLastAccessedTile = null;
        }
        this.mTiles.delete(startPosition);
        return tile;
    }
}
