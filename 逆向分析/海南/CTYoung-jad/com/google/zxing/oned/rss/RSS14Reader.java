// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.google.zxing.oned.rss;

import com.google.zxing.*;
import com.google.zxing.common.BitArray;
import java.util.*;

// Referenced classes of package com.google.zxing.oned.rss:
//            AbstractRSSReader, Pair, FinderPattern, RSSUtils, 
//            DataCharacter

public final class RSS14Reader extends AbstractRSSReader
{

    public RSS14Reader()
    {
    }

    private static void addOrTally(Vector vector, Pair pair)
    {
        if(pair != null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        boolean flag;
        Enumeration enumeration = vector.elements();
        boolean flag1 = false;
        Pair pair1;
        do
        {
            flag = flag1;
            if(!enumeration.hasMoreElements())
                continue; /* Loop/switch isn't completed */
            pair1 = (Pair)enumeration.nextElement();
        } while(pair1.getValue() != pair.getValue());
        pair1.incrementCount();
        flag = true;
        if(flag) goto _L1; else goto _L3
_L3:
        vector.addElement(pair);
        return;
    }

    private void adjustOddEvenCounts(boolean flag, int i)
        throws NotFoundException
    {
        boolean flag1;
        int j;
        boolean flag2;
        int k;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        boolean flag6;
        int l;
        int i1;
        int j1;
        l = count(oddCounts);
        i1 = count(evenCounts);
        j1 = (l + i1) - i;
        if(flag)
            i = 1;
        else
            i = 0;
        if((l & 1) == i)
            flag5 = true;
        else
            flag5 = false;
        if((i1 & 1) == 1)
            flag4 = true;
        else
            flag4 = false;
        flag2 = false;
        flag3 = false;
        j = 0;
        i = 0;
        flag1 = false;
        flag6 = false;
        if(!flag) goto _L2; else goto _L1
_L1:
        if(l <= 12) goto _L4; else goto _L3
_L3:
        k = 1;
_L9:
        if(i1 > 12)
        {
            i = 1;
            flag2 = flag3;
            j = k;
        } else
        {
            i = ((flag6) ? 1 : 0);
            j = k;
            flag2 = flag3;
            if(i1 < 4)
            {
                flag1 = true;
                i = ((flag6) ? 1 : 0);
                j = k;
                flag2 = flag3;
            }
        }
_L6:
        if(j1 == 1)
        {
            if(flag5)
            {
                if(flag4)
                    throw NotFoundException.getNotFoundInstance();
                j = 1;
            } else
            {
                if(!flag4)
                    throw NotFoundException.getNotFoundInstance();
                i = 1;
            }
        } else
        if(j1 == -1)
        {
            if(flag5)
            {
                if(flag4)
                    throw NotFoundException.getNotFoundInstance();
                flag2 = true;
            } else
            {
                if(!flag4)
                    throw NotFoundException.getNotFoundInstance();
                flag1 = true;
            }
        } else
        if(j1 == 0)
        {
            if(!flag5)
                continue; /* Loop/switch isn't completed */
            if(!flag4)
                throw NotFoundException.getNotFoundInstance();
            if(l < i1)
            {
                flag2 = true;
                i = 1;
            } else
            {
                j = 1;
                flag1 = true;
            }
        } else
        {
            throw NotFoundException.getNotFoundInstance();
        }
          goto _L5
_L4:
        k = i;
        if(l < 4)
        {
            flag3 = true;
            k = i;
        }
        continue; /* Loop/switch isn't completed */
_L2:
        if(l > 11)
        {
            k = 1;
            flag3 = flag2;
        } else
        {
            k = j;
            flag3 = flag2;
            if(l < 5)
            {
                flag3 = true;
                k = j;
            }
        }
        if(i1 > 10)
        {
            i = 1;
            j = k;
            flag2 = flag3;
        } else
        {
            i = ((flag6) ? 1 : 0);
            j = k;
            flag2 = flag3;
            if(i1 < 4)
            {
                flag1 = true;
                i = ((flag6) ? 1 : 0);
                j = k;
                flag2 = flag3;
            }
        }
          goto _L6
        if(!flag4) goto _L5; else goto _L7
_L7:
        throw NotFoundException.getNotFoundInstance();
_L5:
        if(flag2)
        {
            if(j != 0)
                throw NotFoundException.getNotFoundInstance();
            increment(oddCounts, oddRoundingErrors);
        }
        if(j != 0)
            decrement(oddCounts, oddRoundingErrors);
        if(flag1)
        {
            if(i != 0)
                throw NotFoundException.getNotFoundInstance();
            increment(evenCounts, oddRoundingErrors);
        }
        if(i != 0)
            decrement(evenCounts, evenRoundingErrors);
        return;
        if(true) goto _L9; else goto _L8
_L8:
    }

    private static boolean checkChecksum(Pair pair, Pair pair1)
    {
        int i = pair.getFinderPattern().getValue();
        int j = pair1.getFinderPattern().getValue();
        if((i != 0 || j != 8) && i == 8)
            if(j != 0);
        int k = pair.getChecksumPortion();
        int l = pair1.getChecksumPortion();
        j = pair.getFinderPattern().getValue() * 9 + pair1.getFinderPattern().getValue();
        i = j;
        if(j > 72)
            i = j - 1;
        j = i;
        if(i > 8)
            j = i - 1;
        return (k + l * 16) % 79 == j;
    }

    private static Result constructResult(Pair pair, Pair pair1)
    {
        Object obj = String.valueOf(0x453af5L * (long)pair.getValue() + (long)pair1.getValue());
        Object obj1 = new StringBuffer(14);
        for(int i = 13 - ((String) (obj)).length(); i > 0; i--)
            ((StringBuffer) (obj1)).append('0');

        ((StringBuffer) (obj1)).append(((String) (obj)));
        int l = 0;
        for(int j = 0; j < 13; j++)
        {
            int j1 = ((StringBuffer) (obj1)).charAt(j) - 48;
            int i1 = j1;
            if((j & 1) == 0)
                i1 = j1 * 3;
            l += i1;
        }

        l = 10 - l % 10;
        int k = l;
        if(l == 10)
            k = 0;
        ((StringBuffer) (obj1)).append(k);
        ResultPoint aresultpoint1[] = pair.getFinderPattern().getResultPoints();
        ResultPoint aresultpoint[] = pair1.getFinderPattern().getResultPoints();
        pair = String.valueOf(((StringBuffer) (obj1)).toString());
        pair1 = aresultpoint1[0];
        obj1 = aresultpoint1[1];
        ResultPoint resultpoint = aresultpoint[0];
        aresultpoint = aresultpoint[1];
        BarcodeFormat barcodeformat = BarcodeFormat.RSS14;
        return new Result(pair, null, new ResultPoint[] {
            pair1, obj1, resultpoint, aresultpoint
        }, barcodeformat);
    }

    private DataCharacter decodeDataCharacter(BitArray bitarray, FinderPattern finderpattern, boolean flag)
        throws NotFoundException
    {
        int ai[] = dataCharacterCounters;
        ai[0] = 0;
        ai[1] = 0;
        ai[2] = 0;
        ai[3] = 0;
        ai[4] = 0;
        ai[5] = 0;
        ai[6] = 0;
        ai[7] = 0;
        float f;
        int l;
        int i1;
        float af[];
        float af1[];
        if(flag)
        {
            recordPatternInReverse(bitarray, finderpattern.getStartEnd()[0], ai);
        } else
        {
            recordPattern(bitarray, finderpattern.getStartEnd()[1] + 1, ai);
            l = 0;
            i = ai.length - 1;
            while(l < i) 
            {
                i1 = ai[l];
                ai[l] = ai[i];
                ai[i] = i1;
                l++;
                i--;
            }
        }
        if(flag)
            l = 16;
        else
            l = 15;
        f = (float)count(ai) / (float)l;
        bitarray = oddCounts;
        finderpattern = evenCounts;
        af = oddRoundingErrors;
        af1 = evenRoundingErrors;
        i1 = 0;
        while(i1 < ai.length) 
        {
            float f1 = (float)ai[i1] / f;
            int j1 = (int)(0.5F + f1);
            int i;
            if(j1 < 1)
            {
                i = 1;
            } else
            {
                i = j1;
                if(j1 > 8)
                    i = 8;
            }
            j1 = i1 >> 1;
            if((i1 & 1) == 0)
            {
                bitarray[j1] = i;
                af[j1] = f1 - (float)i;
            } else
            {
                finderpattern[j1] = i;
                af1[j1] = f1 - (float)i;
            }
            i1++;
        }
        adjustOddEvenCounts(flag, l);
        int j = 0;
        l = 0;
        for(i1 = bitarray.length - 1; i1 >= 0; i1--)
        {
            l = l * 9 + bitarray[i1];
            j += bitarray[i1];
        }

        int j2 = 0;
        i1 = 0;
        for(int k1 = finderpattern.length - 1; k1 >= 0; k1--)
        {
            j2 = j2 * 9 + finderpattern[k1];
            i1 += finderpattern[k1];
        }

        l += j2 * 3;
        if(flag)
            if((j & 1) != 0 || j > 12 || j < 4)
            {
                throw NotFoundException.getNotFoundInstance();
            } else
            {
                j = (12 - j) / 2;
                int l1 = OUTSIDE_ODD_WIDEST[j];
                i1 = RSSUtils.getRSSvalue(bitarray, l1, false);
                l1 = RSSUtils.getRSSvalue(finderpattern, 9 - l1, true);
                return new DataCharacter(i1 * OUTSIDE_EVEN_TOTAL_SUBSET[j] + l1 + OUTSIDE_GSUM[j], l);
            }
        if((i1 & 1) != 0 || i1 > 10 || i1 < 4)
        {
            throw NotFoundException.getNotFoundInstance();
        } else
        {
            int k = (10 - i1) / 2;
            i1 = INSIDE_ODD_WIDEST[k];
            int i2 = RSSUtils.getRSSvalue(bitarray, i1, true);
            return new DataCharacter(RSSUtils.getRSSvalue(finderpattern, 9 - i1, false) * INSIDE_ODD_TOTAL_SUBSET[k] + i2 + INSIDE_GSUM[k], l);
        }
    }

    private Pair decodePair(BitArray bitarray, boolean flag, int i, Hashtable hashtable)
    {
        FinderPattern finderpattern;
        int ai[];
        try
        {
            ai = findFinderPattern(bitarray, 0, flag);
            finderpattern = parseFoundFinderPattern(bitarray, i, flag, ai);
        }
        // Misplaced declaration of an exception variable
        catch(BitArray bitarray)
        {
            return null;
        }
        if(hashtable != null)
            break MISSING_BLOCK_LABEL_140;
        float f;
        float f1;
        for(hashtable = null; hashtable == null; hashtable = (ResultPointCallback)hashtable.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK))
            break MISSING_BLOCK_LABEL_85;

        f1 = (float)(ai[0] + ai[1]) / 2.0F;
        f = f1;
        if(!flag)
            break MISSING_BLOCK_LABEL_67;
        f = (float)(bitarray.getSize() - 1) - f1;
        hashtable.foundPossibleResultPoint(new ResultPoint(f, i));
        hashtable = decodeDataCharacter(bitarray, finderpattern, true);
        bitarray = decodeDataCharacter(bitarray, finderpattern, false);
        return new Pair(hashtable.getValue() * 1597 + bitarray.getValue(), hashtable.getChecksumPortion() + bitarray.getChecksumPortion() * 4, finderpattern);
    }

    private int[] findFinderPattern(BitArray bitarray, int i, boolean flag)
        throws NotFoundException
    {
        int ai[] = decodeFinderCounters;
        ai[0] = 0;
        ai[1] = 0;
        ai[2] = 0;
        ai[3] = 0;
        int l = bitarray.getSize();
        boolean flag2 = false;
        do
        {
label0:
            {
label1:
                {
                    int j;
                    int k;
                    if(i < l)
                    {
                        boolean flag1;
                        if(!bitarray.get(i))
                            flag2 = true;
                        else
                            flag2 = false;
                        if(flag != flag2)
                            break label1;
                    }
                    flag1 = false;
                    j = i;
                    k = i;
                    i = j;
                    j = ((flag1) ? 1 : 0);
                    while(k < l) 
                    {
                        if(bitarray.get(k) ^ flag2)
                        {
                            ai[j] = ai[j] + 1;
                            flag = flag2;
                        } else
                        {
                            if(j == 3)
                            {
                                if(isFinderPattern(ai))
                                    return (new int[] {
                                        i, k
                                    });
                                i += ai[0] + ai[1];
                                ai[0] = ai[2];
                                ai[1] = ai[3];
                                ai[2] = 0;
                                ai[3] = 0;
                                j--;
                            } else
                            {
                                j++;
                            }
                            ai[j] = 1;
                            if(!flag2)
                                flag = true;
                            else
                                flag = false;
                        }
                        k++;
                        flag2 = flag;
                    }
                    break label0;
                }
                i++;
                continue;
            }
            throw NotFoundException.getNotFoundInstance();
        } while(true);
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitarray, int i, boolean flag, int ai[])
        throws NotFoundException
    {
        boolean flag1 = bitarray.get(ai[0]);
        int j;
        for(j = ai[0] - 1; j >= 0 && bitarray.get(j) ^ flag1; j--);
        int i1 = j + 1;
        int k = ai[0];
        int ai1[] = decodeFinderCounters;
        for(j = ai1.length - 1; j > 0; j--)
            ai1[j] = ai1[j - 1];

        ai1[0] = k - i1;
        int k1 = parseFinderValue(ai1, FINDER_PATTERNS);
        k = i1;
        int j1 = ai[1];
        int l = k;
        j = j1;
        if(flag)
        {
            l = bitarray.getSize() - 1 - k;
            j = bitarray.getSize() - 1 - j1;
        }
        return new FinderPattern(k1, new int[] {
            i1, ai[1]
        }, l, j, i);
    }

    public Result decodeRow(int i, BitArray bitarray, Hashtable hashtable)
        throws NotFoundException
    {
        Pair pair = decodePair(bitarray, false, i, hashtable);
        addOrTally(possibleLeftPairs, pair);
        bitarray.reverse();
        hashtable = decodePair(bitarray, true, i, hashtable);
        addOrTally(possibleRightPairs, hashtable);
        bitarray.reverse();
        int k = possibleLeftPairs.size();
        int l = possibleRightPairs.size();
label0:
        for(i = 0; i < k; i++)
        {
            bitarray = (Pair)possibleLeftPairs.elementAt(i);
            if(bitarray.getCount() <= 1)
                continue;
            int j = 0;
            do
            {
                if(j >= l)
                    continue label0;
                hashtable = (Pair)possibleRightPairs.elementAt(j);
                if(hashtable.getCount() > 1 && checkChecksum(bitarray, hashtable))
                    return constructResult(bitarray, hashtable);
                j++;
            } while(true);
        }

        throw NotFoundException.getNotFoundInstance();
    }

    public void reset()
    {
        possibleLeftPairs.setSize(0);
        possibleRightPairs.setSize(0);
    }

    private static final int FINDER_PATTERNS[][];
    private static final int INSIDE_GSUM[] = {
        0, 336, 1036, 1516
    };
    private static final int INSIDE_ODD_TOTAL_SUBSET[] = {
        4, 20, 48, 81
    };
    private static final int INSIDE_ODD_WIDEST[] = {
        2, 4, 6, 8
    };
    private static final int OUTSIDE_EVEN_TOTAL_SUBSET[] = {
        1, 10, 34, 70, 126
    };
    private static final int OUTSIDE_GSUM[] = {
        0, 161, 961, 2015, 2715
    };
    private static final int OUTSIDE_ODD_WIDEST[] = {
        8, 6, 4, 3, 1
    };
    private final Vector possibleLeftPairs = new Vector();
    private final Vector possibleRightPairs = new Vector();

    static 
    {
        int ai[] = {
            3, 5, 5, 1
        };
        int ai1[] = {
            3, 1, 9, 1
        };
        int ai2[] = {
            2, 7, 4, 1
        };
        int ai3[] = {
            2, 5, 6, 1
        };
        FINDER_PATTERNS = (new int[][] {
            new int[] {
                3, 8, 2, 1
            }, ai, new int[] {
                3, 3, 7, 1
            }, ai1, ai2, ai3, new int[] {
                2, 3, 8, 1
            }, new int[] {
                1, 5, 7, 1
            }, new int[] {
                1, 3, 9, 1
            }
        });
    }
}
